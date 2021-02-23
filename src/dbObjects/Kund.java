package dbObjects;

public class Kund {
//    public static Map<Integer, Kund> map = new HashMap<>();
    private int id;
    private String namn;
    private String adress;
    private Ort ort;

    public Kund(int id, String namn, String adress, Ort ort){
        setNamn(namn);
        this.id = id;
        this.adress = adress;
        this.ort = ort;
//        map.put(id, this);
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
