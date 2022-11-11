import java.io.Serializable;

public class Produse extends ProdusComandat implements Serializable {
    private String nume;
    private double pret;

    public Produse() {

    }

    public Produse(String nume, double pret) {
        this.nume = nume;
        this.pret = pret;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }
}
