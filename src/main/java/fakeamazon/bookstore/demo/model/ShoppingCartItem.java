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


    /**
     * Retrieve the quantity of the shopping cart item.
     *
     * @return Item quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity of the shopping cart item.
     *
     * @param quantity The new quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get the associated book entity.
     *
     * @return The internal book entity
     */
    public Book getBook() {
        return book;
    }

    /**
     * Set the internal book entity.
     *
     * @param book The internal book entity
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Get the ID of the shopping cart item.
     *
     * @return The item ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the book.
     *
     * @param id The new item ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the customer associated with this item.
     *
     * @return Owner of the item
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Set the owner of this item
     *
     * @param customer Owner customer entity
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}