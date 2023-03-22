package fakeamazon.bookstore.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name="cart_items")
public class ShoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    private Book book;

    @ManyToOne
    private Customer customer;

    @Min(1)
    @Column(name = "quantity")
    private int quantity;

    public ShoppingCartItem() {

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}