import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
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

    String getPrisFromDatabase(int index) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("Select * from skomodell where id = "+index);
            rs.next();
            return rs.getString("Pris");

        }

    }
    String getLagerstatusFromDatabase(int index) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("Select * from skomodell where id = "+index);
            rs.next();
            return rs.getString("lagerstatus");

        }

    }

    ArrayList getStorlekarFromDatabase(int index) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            ArrayList temp = new ArrayList();
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select * from storlek join storleksmappning on storlek.id = storleksmappning.storlekID join skomodell on storleksmappning.skomodellID = skomodell.id where skomodell.id ="+index);
            while (rs.next()) {
                temp.add(rs.getString("skostorlek"));
            }
            return temp;
        }
    }

    ArrayList getfärgFromDatabase(int index) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            ArrayList temp = new ArrayList();
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select * from färg join färgmappning on färg.id = färgmappning.färgID join skomodell on färgmappning.skomodellID = skomodell.id where skomodell.id = "+index);
            while (rs.next()) {
                temp.add(rs.getString("Färg"));
            }
            return temp;
        }
    }
    ArrayList getskomodellFromDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            ArrayList temp = new ArrayList();
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select *  from skomodell");
            while (rs.next()) {
                temp.add(rs.getString("id"));
            }
            return temp;
        }
    }
     void addToCart(int skomodelId, int kundId,int storlek, String färg) throws ClassNotFoundException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            stmt.executeQuery("call Stored_Procedure_Add_to_Cart ("+skomodelId+","+kundId+","+storlek+",'"+färg+"')");
            System.out.println("kallad på!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
//private String getSkomodellFromDatabase(int skomodellId) throws SQLException, ClassNotFoundException {
//        ResultSet rs = getData("Select skomodell from skomodell where id = ",skomodellId);
//       rs.next();
//       return rs.getString("skomodell");
//}
}
