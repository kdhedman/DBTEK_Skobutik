import dbObjects.Kund;

public class ActiveKund {
    private static Kund kund;

    public static void setKund(Kund kund) {
        ActiveKund.kund = kund;
    }

    public static Kund getKund() {
        return kund;
    }
}
