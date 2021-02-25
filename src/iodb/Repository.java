package iodb;

import dbObjects.Färg;
import dbObjects.LagerStatus;
import dbObjects.Skomodell;
import dbObjects.Storlek;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Repository {

    private Properties p = new Properties();
    private String connection, user, password;

    public Repository() {
        try {
            p.load(new FileInputStream("res/dbc.properties"));
            connection = p.getProperty("connection");
            user = p.getProperty("user");
            password = p.getProperty("pw");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!testingDBConnection()) {
            System.exit(1);
        }
    }

    private boolean testingDBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection con = DriverManager.getConnection(
                connection,
                user,
                password)) {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select * from kund limit 1");
            while (rs.next()) {
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Someting wrong with Database Connection");
            e.printStackTrace();
            return false;
        }
    }

    public String getSkomodellFromDatabase(int index) {
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("Select skomodell from skomodell where id = " + index);
            rs.next();
            return rs.getString("skomodell");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public String getPrisFromDatabase(int index) {
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("Select * from skomodell where id = " + index);
            rs.next();
            return rs.getString("Pris");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    public String getLagerstatusFromDatabase(int index, int storlek, String färg) {
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = stmt.executeQuery("Select lagerstatus from lagermappning where skomodellid = " + index + " and storlekid = (select id from storlek where skostorlek= " + storlek + ") and färgid = (select färg.id from färg where färg = '" + färg + "')");
            while (rs.next()) {
                return rs.getString("lagerstatus");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public ArrayList getStorlekarFromDatabase(int index) {
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            ArrayList temp = new ArrayList();
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select distinct skostorlek  from lagermappning join storlek on storlek.id = lagermappning.storlekID where lagermappning.skomodellid =" + index);
            while (rs.next()) {
                temp.add(rs.getString("skostorlek"));
            }
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList getfärgFromDatabase(int skomodellID, int storlek) {
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            ArrayList temp = new ArrayList();
            PreparedStatement stmt = con.prepareStatement("select *  from färg " +
                    "join lagermappning on färg.id = lagermappning.FärgId " +
                    "where SkomodellId = ? and " +
                    "StorlekId =(select id from storlek where  Skostorlek = ?)");
            stmt.setString(1,String.valueOf(skomodellID));
            stmt.setString(2, String.valueOf(storlek));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                temp.add(rs.getString("Färg"));
            }
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList getskomodellFromDatabase() {
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            ArrayList temp = new ArrayList();
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select *  from skomodell");
            while (rs.next()) {
                temp.add(rs.getString("id"));
            }
            return temp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean tryLogin(String username, String password) {
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addToCart(int skomodelId, int kundId, int storlek, String färg) {
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            PreparedStatement stmt = con.prepareStatement("call Stored_Procedure_Add_to_Cart (?,?,?,?)");
            stmt.setString(1, String.valueOf(skomodelId));
            stmt.setString(2, String.valueOf(kundId));
            stmt.setString(3, String.valueOf(storlek));
            stmt.setString(4, String.valueOf(färg));
            stmt.executeQuery();
            System.out.println("kallad på!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getKundIDFromNamn(String namn) {
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            PreparedStatement stmt = con.prepareStatement("select id from kund where namn = ?");
            stmt.setString(1, namn);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String[][] skomodellBetyg(String skomodell) {
        List<String[]> rsList = new ArrayList<>();
        String[][] content;
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            PreparedStatement stmt = con.prepareStatement("select kund.namn, betygskala.betyg, betyg.kommentar from kund " +
                    "join betyg on kund.id = kundID " +
                    "join betygskala on betyg.betygskalaID = betygskala.id " +
                    "join skomodell on betyg.skomodellID = skomodell.id " +
                    "where skomodell = ?");
            stmt.setString(1, skomodell);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String[] temp = {
                        rs.getString("namn"),
                        rs.getString("betyg"),
                        rs.getString("kommentar")
                };
                rsList.add(temp);
            }
            content = new String[rsList.size()][3];
            for (int i = 0; i < rsList.size(); i++) {
                content[i] = rsList.get(i);
            }
            return content;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[][] getKundsBeställningar(String namn) {
        List<String[]> rsResult = new ArrayList<>();
        String[][] content;
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            PreparedStatement stmt = con.prepareStatement("select Kvantitet, skomodell, färg, skostorlek, pris from beställning" +
                    "    join leverans on leveransID = leverans.id" +
                    "    join kund on kundID = kund.id" +
                    "    join lagermappning on lagermappningsid = lagermappning.id" +
                    "    join skomodell on skomodellID = skomodell.id" +
                    "    join färg on färgid = färg.id" +
                    "    join storlek on storlekid = storlek.id" +
                    "    where kund.namn = ? and Datum is null;");
            stmt.setString(1, namn);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String[] temp = {
                        rs.getString("kvantitet"),
                        rs.getString("skomodell"),
                        rs.getString("färg"),
                        rs.getString("skostorlek"),
                        rs.getString("pris"),
                };
                rsResult.add(temp);
            }
            content = new String[rsResult.size()][5];
            for (int i = 0; i < rsResult.size(); i++) {
                content[i] = rsResult.get(i);
            }
            return content;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getSkomodellIDbyModell(String model) {
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            PreparedStatement stmt = con.prepareStatement("select id from skomodell where skomodell = ?");
            stmt.setString(1, model);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean setRating(int rate, String comment, int kundID, int skomodellID) {
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            PreparedStatement stmt = con.prepareStatement("call Stored_Procedure_Rate(?,?,?,?)");
            stmt.setString(1, "" + rate);
            stmt.setString(2, comment);
            stmt.setString(3, "" + kundID);
            stmt.setString(4, "" + skomodellID);
            stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public float getSkomodellAverageBetyg(int modelID) {
        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            PreparedStatement stmt = con.prepareStatement("select get_medelbetyg(?) as Medelbetyg");
            stmt.setFloat(1, modelID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getFloat("Medelbetyg");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Skomodell> getSkomodellerAsOBjects() {
        Map<Integer, Färg> colorMap = new HashMap<>();
        Map<Integer, Storlek> sizeMap = new HashMap<>();
        List<Skomodell> result = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(
                this.connection,
                this.user,
                this.password)) {
            Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select * from färg");
            //Set ColorMap
            while (rs.next()) {
                Färg tempColor = new Färg(rs.getInt("id"), rs.getString("färg"));
                colorMap.put(tempColor.id, tempColor);
            }
            //Set SizeMap
            rs = stmt.executeQuery("select * from storlek");
            while (rs.next()) {
                Storlek tempSize = new Storlek(rs.getInt("id"), rs.getInt("skostorlek"));
                sizeMap.put(tempSize.id, tempSize);
            }
            //Fix shoes
            rs = stmt.executeQuery("select * from skomodell");
            while (rs.next()) {
                Skomodell skomodell = new Skomodell(
                        rs.getInt("id"),
                        rs.getString("skomodell"),
                        rs.getInt("pris"),
                        null
                );

                //add size to list
                PreparedStatement stmtSizes = con.prepareStatement("select distinct StorlekId " +
                        "from lagermappning " +
                        "where SkomodellId = ?");
                stmtSizes.setString(1, String.valueOf(skomodell.id));
                ResultSet rsSize = stmtSizes.executeQuery();

                while (rsSize.next()) {
                    int storlekID = rsSize.getInt("StorlekID");
                    skomodell.sizeColorQuantityMap.put(sizeMap.get(storlekID), new HashMap<>());

                    //add color to sizeColorMap
                    PreparedStatement stmtColors = con.prepareStatement("select distinct färgid " +
                            "from lagermappning " +
                            "where SkomodellId = ? and StorlekId = ?");
                    stmtColors.setString(1, String.valueOf(skomodell.id));
                    stmtColors.setString(2, String.valueOf(storlekID));
                    ResultSet rsColor = stmtColors.executeQuery();

                    while (rsColor.next()) {
                        int colorID = rsColor.getInt("FärgID");

                        PreparedStatement stmtQuantity = con.prepareStatement("select id, Lagerstatus " +
                                "from lagermappning " +
                                "where SkomodellId = ? and StorlekId = ? and FärgId = ?");
                        stmtQuantity.setString(1, String.valueOf(skomodell.id));
                        stmtQuantity.setString(2, String.valueOf(storlekID));
                        stmtQuantity.setString(3, String.valueOf(colorID));
                        ResultSet rsQuantity = stmtQuantity.executeQuery();
                        while(rsQuantity.next()){
                            LagerStatus lagerStatus = new LagerStatus(rsQuantity.getInt("id"), rsQuantity.getInt("LagerStatus"));
                            skomodell.sizeColorQuantityMap.get(sizeMap.get(storlekID)).put(colorMap.get(colorID), lagerStatus);
                        }


//                        skomodell.sizeColorQuantityMap.get(sizeMap.get(storlekID)).put(colorMap.get(colorID), );
                    }
                }

                result.add(skomodell);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}