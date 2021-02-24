package dbObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Skomodell {
//    public static Map<Integer, Skomodell> map = new HashMap<>();
    public int id;
    public String skomodell;
    public int pris;
    public Märke märke;
    public Map<Storlek, Map<Färg, LagerStatus>> sizeColorQuantityMap = new HashMap<>();
    public List<Kategori> kategorier = new ArrayList<>();

    public Skomodell(int id, String skomodell, int pris, Märke märke){
        this.id = id;
        this.skomodell = skomodell;
        this.pris = pris;
        this.märke = märke;
//        map.put(id, this);
    }
}
