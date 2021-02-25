package dbObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Skomodell {
    public static Map<Integer, Skomodell> map = new HashMap<>();
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
        map.put(id, this);
    }

    public String getSkomodell() {
        return skomodell;
    }

    @Override
    public String toString() {
        return skomodell;
    }

    public List<Storlek> getAllStorlek(){
        return sizeColorQuantityMap.keySet().stream().collect(Collectors.toList());
    }

    public List<Färg> getAllFärg(){
        List<Färg> colors = new ArrayList<>();
        List<Storlek> storleker = getAllStorlek();
        sizeColorQuantityMap.keySet().stream().forEach(size -> {
            sizeColorQuantityMap.get(size).keySet().stream().forEach(c -> {
                if(!colors.contains(c)) colors.add(c);
            });
        });
        return colors;
    }
}
