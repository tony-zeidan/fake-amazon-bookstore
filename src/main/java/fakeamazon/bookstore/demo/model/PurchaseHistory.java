package fakeamazon.bookstore.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PurchaseHistory {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PurchaseItem> history;

    public PurchaseHistory() {}

    public void addToHistory(PurchaseItem purchased) {this.history.add(purchased);}

    public void setHistory(List<PurchaseItem> history) {
        this.history = history;
    }

    public List<PurchaseItem> getHistory() {
        return this.history;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
