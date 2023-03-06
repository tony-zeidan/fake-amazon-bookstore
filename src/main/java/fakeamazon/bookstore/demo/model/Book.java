package fakeamazon.bookstore.demo.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    public Book() {
    }

    public Book(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
/**
     * Books need the following items:
     * - id: long
     * - quantity: int
     * - isbn: string
     * - picture: string (base64 string)
     * - description: string
     * - publisher: string
     */

}
