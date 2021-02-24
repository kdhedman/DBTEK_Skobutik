package dbObjects;

import java.util.ArrayList;
import java.util.List;

public class Skomodell {
//    public static Map<Integer, Skomodell> map = new HashMap<>();
    int id;
    String skomodell;
    int pris;
    Märke märke;
    private List<Storlek> storlekar = new ArrayList<>();
    private List<Färg> färger = new ArrayList<>();
    private List<Kategori> kategorier = new ArrayList<>();

    public Skomodell(int id, String skomodell, int pris, Märke märke){
        this.id = id;
        this.skomodell = skomodell;
        this.pris = pris;
        this.märke = märke;
//        map.put(id, this);
    }

    public List<Storlek> getStorlekar(){
        if(storlekar.isEmpty()){

        }
        return storlekar;
    }
}
