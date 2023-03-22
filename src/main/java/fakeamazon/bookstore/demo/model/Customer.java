package fakeamazon.bookstore.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Customer {

    @Id
    @Column(name="username")
    private String username;

    @Column(name="cart")
    @OneToMany(fetch=FetchType.EAGER, mappedBy = "customer")
    @JsonIgnore
    private List<ShoppingCartItem> cart;

    @OneToOne(cascade = CascadeType.ALL)
    private PurchaseHistory history;

    public Customer() {}

    public List<ShoppingCartItem> getCart() {
        return cart;
    }

    public void setCart(List<ShoppingCartItem> cart) {
        this.cart = cart;
    }


    public PurchaseHistory getHistory() {
        return history;
    }

    public void addToCart(ShoppingCartItem item) {
        this.cart.add(item);
    }

    public void setHistory(PurchaseHistory history) {
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
