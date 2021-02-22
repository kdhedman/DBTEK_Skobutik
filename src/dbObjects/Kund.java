package dbObjects;

/**
 * Created by David Hedman <br>
 * Date: 2021-02-22 <br>
 * Time: 16:19 <br>
 * Project: DBTEK_Skobutik <br>
 * Copyright: Nackademin <br>
 */
public class Kund {
    private String namn;
    private String adress;
    private Ort ort;

    public Kund(String namn){

    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String name){
        if(name == null || name.isBlank()){
            throw new NullPointerException("Namn can not be empty");
        }
        namn = name;
    }
}