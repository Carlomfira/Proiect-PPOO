import java.io.Serializable;

public class ProdusComandat implements Serializable {
    private Produse produse;
    private int cantitate;

    public ProdusComandat() {

    }

    public ProdusComandat(Produse product, int quantity) {
        this.produse = produse;
        this.cantitate = cantitate;
    }

    public Produse getProduct() {
        return produse;
    }

    public void setProduct(Produse product) {
        this.produse = produse;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
}
