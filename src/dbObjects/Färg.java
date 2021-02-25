package dbObjects;

import java.util.HashMap;
import java.util.Map;

public class Färg {
    public static Map<Integer, Färg> map = new HashMap<>();
    public int id;
    public String färg;

    public Färg(int id, String färg) {
        this.id = id;
        this.färg = färg;
        map.put(id, this);
    }

    public String getFärg() {
        return färg;
    }

    @Override
    public String toString() {
        return färg;
    }
}
