package fakeamazon.bookstore.demo.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * Books need the following items:
 * - id: long
 * - quantity: int
 * - isbn: string
 * - picture: string (base64 string)
 * - description: string
 * - publisher: string
 */

@Entity
@Table(name = "books")
public class Book {

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="cart_item_id", referencedColumnName="id")
    private List<ShoppingCartItem> shoppingCartItem;

    public Book() {

    }

    public Book(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int quantity;

    @Column(unique=true)
    private String isbn;

    @Lob
    private String picture;

    private String description;

    private String publisher;


    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("BOOKOBJ: (name=%s, isbn=%s, publisher=%s, quantity=%d, description=%s, picture=%s)", this.name, this.isbn, this.publisher, this.quantity, this.description, this.picture);
    }

    public List<ShoppingCartItem> getShoppingCartItem() {
        return shoppingCartItem;
    }

    public void setShoppingCartItem(List<ShoppingCartItem> shoppingCartItem) {
        this.shoppingCartItem = shoppingCartItem;
    }
}
