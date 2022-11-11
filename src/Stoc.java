import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stoc implements Serializable {
    private List<Produse> produse;
    private List<Comanda> orders;

    public Stoc() {
        produse = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public List<Produse> getProducts() {
        return produse;
    }

    public void setProducts(List<Produse> products) {
        this.produse = products;
    }

    public boolean addProduct(Produse product) {
        for (Produse p : produse) {
            if (p.getNume().equals(product.getNume())) {
                return false;
            }
        }
        produse.add(product);
        return true;
    }

    public boolean removeProduct(String nume) {
        Produse toDelete = null;
        for (Produse p : produse) {
            if (p.getNume().equals(nume)) {
                toDelete = p;
                break;
            }
        }
        if (toDelete != null) {
            produse.remove(toDelete);
            return true;
        }
        return false;
    }

    public Produse getProductByNume(String nume) {
        for (Produse p : produse) {
            if (p.getNume().equals(nume)) {
                return p;
            }
        }
        return null;
    }

    public Comanda getOrderByNume(String nume) {
        for (Comanda order : orders) {
            if (order.getFilenume().equals(nume)) {
                return order;
            }
        }
        return null;
    }

    public void addOrder(Comanda order) {
        orders.add(order);
    }

    public void removeOrder(Comanda order) {
        orders.remove(order);
    }
}
