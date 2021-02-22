package dbObjects;


public class Kund {
    private int id;
    private String namn;
    private String adress;
    private Ort ort;

    public Kund(String namn, int id){
        setNamn(namn);
        this.id = id;
    }

    public int getId() {
        return id;
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
