package dbObjects;

public class Storlek {
//    public static Map<Integer, Storlek> map = new HashMap<>();
    public int id;
    public int skostorlek;

    public Storlek(int id, int skostorlek) {
        this.id = id;
        this.skostorlek = skostorlek;
//        map.put(id, this);
    }

    public int getSkostorlek() {
        return skostorlek;
    }

    @Override
    public String toString() {
        return "Storlek{" +
                "id=" + id +
                ", skostorlek=" + skostorlek +
                '}';
    }
}
