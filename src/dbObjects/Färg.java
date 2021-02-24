package dbObjects;

public class Färg {
//    public static Map<Integer, Färg> map = new HashMap<>();
    public int id;
    public String färg;

    public Färg(int id, String färg) {
        this.id = id;
        this.färg = färg;
//        map.put(id, this);
    }

    @Override
    public String toString() {
        return "Färg{" +
                "id=" + id +
                ", färg='" + färg + '\'' +
                '}';
    }
}
