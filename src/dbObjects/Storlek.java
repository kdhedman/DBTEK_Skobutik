package dbObjects;

import java.util.HashMap;
import java.util.Map;

public class Storlek implements Comparable<Storlek>{
    public static Map<Integer, Storlek> map = new HashMap<>();
    public int id;
    public int skostorlek;

    public Storlek(int id, int skostorlek) {
        this.id = id;
        this.skostorlek = skostorlek;
        map.put(id, this);
    }

    public int getSkostorlek() {
        return skostorlek;
    }

    @Override
    public String toString() {
        return String.valueOf(skostorlek);
    }


    @Override
    public int compareTo(Storlek o) {
        return o.skostorlek < this.skostorlek ? 1 : o.skostorlek == this.skostorlek ? 0 : -1;
    }
}
