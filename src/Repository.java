import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Repository {

    private Properties p = new Properties();

    public Repository() throws IOException {
        p.load(new FileInputStream("res/dbc.properties"));
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Repository repo = new Repository();
        repo.testingDBConnection();
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
}
