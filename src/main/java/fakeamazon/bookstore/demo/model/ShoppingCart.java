package fakeamazon.bookstore.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ShoppingCartItem> cart;

    public ShoppingCart() {}

    public void addToHistory(ShoppingCartItem purchased) {}

    public void setHistory(List<ShoppingCartItem> history) {
        this.cart = history;
    }

    public List<ShoppingCartItem> getHistory() {
        return this.cart;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
