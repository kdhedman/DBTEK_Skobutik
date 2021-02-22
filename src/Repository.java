import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Repository {

    private Properties p = new Properties();

    public Repository() {
        try{
            p.load(new FileInputStream("res/dbc.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Repository repo = new Repository();
        System.out.println(repo.getSkomodellFromDatabase(1));
    }

    private void testingDBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    private void showInventory (String query)  {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
     String getSkomodellFromDatabase(int index) {
         try {
             Class.forName("com.mysql.cj.jdbc.Driver");
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         }
         try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

           ResultSet rs = stmt.executeQuery("Select skomodell from skomodell where id = "+index);
             rs.next();
             return rs.getString("skomodell");

        } catch (SQLException throwables) {
             throwables.printStackTrace();
         }

         return null;
     }

    String getPrisFromDatabase(int index) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("Select * from skomodell where id = "+index);
            rs.next();
            return rs.getString("Pris");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

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
        return null;
    }

    ArrayList getStorlekarFromDatabase(int index) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    ArrayList getfärgFromDatabase(int index) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
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
        return null;
    }

    boolean tryLogin(String username, String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            String dbUsername = "";
            String dbPassword = "";
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select namn as name, passerord as password from kund where namn like '"+username+"';");
            while (rs.next()) {
                dbUsername = rs.getString("name");
                dbPassword = rs.getString("password");
            }
            return ((dbUsername.equalsIgnoreCase(username)) && (dbPassword.equalsIgnoreCase(password)));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
