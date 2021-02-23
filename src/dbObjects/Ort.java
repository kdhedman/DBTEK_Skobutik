package dbObjects;

import java.util.Objects;

public class Ort {
//    public static Map<Integer, Ort> map = new HashMap<>();
    int id;
    String namn;

    public Ort(int id, String namn){
        this.id = Objects.requireNonNull(id, "id can not be null");
        setNamn(namn);
//        map.put(id, this);
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn){
        if(namn.isEmpty()) throw new IllegalArgumentException("namn can not be empty");
        this.namn = Objects.requireNonNull(namn, "namn can not be null");
    }
}
