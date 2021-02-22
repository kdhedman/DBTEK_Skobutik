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

    String getLagerstatusFromDatabase(int index, int storlek, String färg)  {
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

            ResultSet rs = stmt.executeQuery("Select lagerstatus from lagermappning where skomodellid = "+index+" and storlekid = (select id from storlek where skostorlek= "+storlek+") and färgid = (select färg.id from färg where färg = '"+färg+"')");
            rs.next();
            return rs.getString("lagerstatus");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
            ResultSet rs = stmt.executeQuery("select * from lagermappning join storlek on storlek.id = lagermappning.storlekID where lagermappning.skomodellid ="+index);
            while (rs.next()) {
                temp.add(rs.getString("skostorlek"));
            }
            return temp;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    ArrayList getfärgFromDatabase(int index, int storlek) {
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
            ResultSet rs = stmt.executeQuery("select *  from färg join lagermappning on färg.id = lagermappning.FärgId where SkomodellId ="+index+" and StorlekId =(select id from storlek where  Skostorlek = "+storlek+")");
            while (rs.next()) {
                temp.add(rs.getString("Färg"));
            }
            return temp;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    ArrayList getskomodellFromDatabase() {
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
            ResultSet rs = stmt.executeQuery("select *  from skomodell");
            while (rs.next()) {
                temp.add(rs.getString("id"));
            }
            return temp;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
            PreparedStatement stmt = con.prepareStatement("select namn as name, passerord as password from kund where namn like ?;");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
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



    void getKundsBeställningar(){

    }
}