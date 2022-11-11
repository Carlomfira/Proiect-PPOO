import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Comanda implements Serializable {
    private String filenume;
    private List<ProdusComandat> items;

    public Comanda() {
        items = new ArrayList<>();
    }

    public Comanda(String filenume) {
        this();
        this.filenume = filenume;
    }

    public String getFilenume() {
        return filenume;
    }

    public void setFilenume(String filenume) {
        this.filenume = filenume;
    }

    public List<ProdusComandat> getItems() {
        return items;
    }

    public void setItems(List<ProdusComandat> items) {
        this.items = items;
    }

    public void addOrderedItem(Produse item) {
        items.add(item);
    }

    public void deleteOrderedItem(ProdusComandat item) {
        items.remove(item);
    }

}
