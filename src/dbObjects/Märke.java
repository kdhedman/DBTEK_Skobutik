package dbObjects;

import java.util.List;

public class Märke {
//    public static Map<Integer, Märke> map = new HashMap<>();
    int id;
    String märke;
    List<Skomodell> skomodellList;

    public Märke(int id, String märke) {
        this.id = id;
        this.märke = märke;
//        map.put(id, this);
    }
}
