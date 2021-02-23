package dbObjects;

public class Betyg {
//    public static Map<Integer, Betyg> map = new HashMap<>();
    int id;
    String kommentar;
    Betygsskala betygsskala;
    Kund kund;
    Skomodell skomodell;

    public Betyg(int id, String kommentar, Betygsskala betygsskala, Kund kund, Skomodell skomodell){
        this.id = id;
        this.kommentar = kommentar;
        this.betygsskala = betygsskala;
        this.kund = kund;
        this.skomodell = skomodell;
//        map.put(id, this);
    }
}
