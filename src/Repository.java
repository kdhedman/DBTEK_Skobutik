import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            while (rs.next()) {
                return rs.getString("lagerstatus");
            }

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
            ResultSet rs = stmt.executeQuery("select distinct skostorlek  from lagermappning join storlek on storlek.id = lagermappning.storlekID where lagermappning.skomodellid ="+index);
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

    void addToCart(int skomodelId, int kundId,int storlek, String färg) {
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
            stmt.executeQuery("call Stored_Procedure_Add_to_Cart ("+skomodelId+","+kundId+","+storlek+",'"+färg+"')");
            System.out.println("kallad på!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getKundIDFromNamn(String namn){
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
            PreparedStatement stmt = con.prepareStatement("select id from kund where namn = ?");
            stmt.setString(1,namn);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public String[][] skomodellBetyg(String skomodell){
        List<String> result = new ArrayList<>();
        String[][] content;
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            PreparedStatement stmt = con.prepareStatement("select kund.namn, betygskala.betyg, betyg.kommentar  from kund join betyg  on kund.id = kundID join betygskala  on betyg.betygskalaID = betygskala.id join skomodell on betyg.skomodellID = skomodell.id where skomodell = '"+skomodell+"'");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                StringBuilder sb = new StringBuilder();
                sb.append(rs.getString("namn") + "#");
                sb.append(rs.getString("betyg") +"#");
                sb.append(rs.getString("kommentar") +"#");
                result.add(sb.toString());
            }
            content = new String[result.size()][3];
            for (int i = 0; i < result.size(); i++) {
                String[] temp = result.get(i).split("#");
                content[i] = temp;
            }
            return content;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public String[][] getKundsBeställningar(String namn){
        List<String> result = new ArrayList<>();
        String[][] content;
        try(Connection con = DriverManager.getConnection(
                p.getProperty("connection"),
                p.getProperty("user"),
                p.getProperty("pw")))
        {
            PreparedStatement stmt = con.prepareStatement("select Kvantitet, skomodell, färg, skostorlek, pris from beställning" +
                    "    join leverans on leveransID = leverans.id" +
                    "    join kund on kundID = kund.id" +
                    "    join lagermappning on lagermappningsid = lagermappning.id" +
                    "    join skomodell on skomodellID = skomodell.id" +
                    "    join färg on färgid = färg.id" +
                    "    join storlek on storlekid = storlek.id" +
                    "    where kund.namn = ? and Datum is null;");
            stmt.setString(1,namn);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                StringBuilder sb = new StringBuilder();
                sb.append(rs.getString("kvantitet") + "#");
                sb.append(rs.getString("skomodell") +"#");
                sb.append(rs.getString("färg") +"#");
                sb.append(rs.getString("skostorlek") +"#");
                sb.append(rs.getString("pris"));
                result.add(sb.toString());
            }
            content = new String[result.size()][5];
            for (int i = 0; i < result.size(); i++) {
                System.out.println("sbResult " + result.get(i).toString());
                String[] temp = result.get(i).split("#");
                System.out.println("temp[] " + Arrays.toString(temp));
                content[i] = temp;
            }
            return content;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int getSkomodellIDbyModell(String model){
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
            PreparedStatement stmt = con.prepareStatement("select id from skomodell where skomodell = ?");
            stmt.setString(1,model);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                return rs.getInt("id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public boolean setRating(int rate, String comment, int kundID, int skomodellID){
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
            PreparedStatement stmt = con.prepareStatement("call Stored_Procedure_Rate(?,?,?,?)");
            stmt.setString(1,""+rate);
            stmt.setString(2, comment);
            stmt.setString(3, ""+kundID);
            stmt.setString(4, ""+skomodellID);
            stmt.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }

    public int getSkomodellAverageBetyg(int modelID){
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
            PreparedStatement stmt = con.prepareStatement("select get_medelbetyg(?) as Medelbetyg");
            stmt.setInt(1,modelID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                return rs.getInt("Medelbetyg");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}