package fakeamazon.bookstore.demo.dto;

/**
 * The Data Transfer Object (DTO) used to update the quantity of a particular book available
 * to users through the application.
 *
 * @see fakeamazon.bookstore.demo.controller.rest.BookOwnerRestController#updateInventory(BookQuantityDTO)
 */
public class BookQuantityDTO {
    private long id;
    private int quantity;

    /**
     * @return the ID of the book to update
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the ID of the book to update
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the quantity of books to add to the system
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity of books to add to the system
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
