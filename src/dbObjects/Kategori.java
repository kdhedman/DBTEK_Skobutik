package dbObjects;

public class Kategori {
//    public static Map<Integer, Kategori> map = new HashMap<>();
    int id;
    String kategori;

    public Kategori(int id, String kategori) {
        this.id = id;
        this.kategori = kategori;
//        map.put(id, this);
    }
}
