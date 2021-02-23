package dbObjects;

import java.util.ArrayList;
import java.util.List;

public class Skomodell {
//    public static Map<Integer, Skomodell> map = new HashMap<>();
    int id;
    String skomodell;
    int pris;
    Märke märke;
    List<Storlek> storlekar = new ArrayList<>();
    List<Färg> färger = new ArrayList<>();
    List<Kategori> kategorier = new ArrayList<>();

    public Skomodell(int id, String skomodell, int pris, Märke märke){
        this.id = id;
        this.skomodell = skomodell;
        this.pris = pris;
        this.märke = märke;
//        map.put(id, this);
    }
}
