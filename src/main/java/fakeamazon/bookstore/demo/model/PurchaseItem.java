package fakeamazon.bookstore.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Book book;

    @Min(1)
    @Column(name = "QUANTITY")
    private int quantity;

    @CreatedDate
    @Column(name = "CREATION_DATE", nullable = false, updatable = false)
    private Date createdAt;

    public PurchaseItem() {

    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
}