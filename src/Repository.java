import com.mysql.cj.protocol.Resultset;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Repository {

    private Properties p = new Properties();

    public Repository() throws IOException {
        p.load(new FileInputStream("res/dbc.properties"));
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        Repository repo = new Repository();
        System.out.println(repo.getSkomodellFromDatabase(1));
    }

    private void testingDBConnection() throws ClassNotFoundException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
           Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
           ResultSet rs = stmt.executeQuery("SELECT namn from kund");
           while(rs.next()){
               System.out.println(rs.getString("namn"));

           }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void showInventory (String query) throws IOException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                System.out.print(rs.getString("skomodell"));
                System.out.print(" Lagerstatus: ");
                System.out.println(rs.getInt("lagerstatus"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
     String getSkomodellFromDatabase(int index) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

           ResultSet rs = stmt.executeQuery("Select skomodell from skomodell where id = "+index);
             rs.next();
             return rs.getString("skomodell");

        }

}
//private String getSkomodellFromDatabase(int skomodellId) throws SQLException, ClassNotFoundException {
//        ResultSet rs = getData("Select skomodell from skomodell where id = ",skomodellId);
//       rs.next();
//       return rs.getString("skomodell");
//}
}
