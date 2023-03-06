package fakeamazon.bookstore.demo.model;

import jakarta.persistence.*;

@Entity
public class Book {

    /**
     * Books need the following items:
     * - id: long
     * - quantity: int
     * - isbn: string
     * - picture: string (base64 string)
     * - description: string
     * - publisher: string
     */

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int quantity;

    @Column(unique=true)
    private String isbn;

    @Lob
    private byte[] picture;

    private String description;

    private String publisher;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
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
}
