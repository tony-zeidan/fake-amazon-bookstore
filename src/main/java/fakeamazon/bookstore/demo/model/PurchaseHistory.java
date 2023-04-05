package fakeamazon.bookstore.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PurchaseHistory {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PurchaseItem> purchaseItemHistory;

    /**
     * Contains a list of items that have been previously purchased by the user.
     */
    public PurchaseHistory() {}

    public void addToHistory(PurchaseItem purchased) {this.purchaseItemHistory.add(purchased);}

    public void setPurchaseItemHistory(List<PurchaseItem> history) {
        this.purchaseItemHistory = history;
    }

    public List<PurchaseItem> getPurchaseItemHistory() {
        return this.purchaseItemHistory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
