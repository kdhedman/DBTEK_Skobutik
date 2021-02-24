package dbObjects;

public class LagerStatus {
    public int id;
    public int lagerstatus;

    public LagerStatus(int id, int lagerstatus) {
        this.id = id;
        this.lagerstatus = lagerstatus;
    }

    @Override
    public String toString() {
        return "LagerStatus{" +
                "id=" + id +
                ", lagerstatus=" + lagerstatus +
                '}';
    }
}
