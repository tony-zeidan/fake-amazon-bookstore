package fakeamazon.bookstore.demo.dto;

import org.springframework.security.core.Authentication;

/**
 * The Data Transfer Object (DTO) used to select a single book by its ID in a type-safe manner;
 *
 * @see fakeamazon.bookstore.demo.controller.rest.UserRestController#removeFromCart(Authentication, BookIdDTO)
 * @see fakeamazon.bookstore.demo.services.ShoppingCartService#removeCartItem(Authentication, BookIdDTO)
 */
public class BookIdDTO {
    private long id;

    /**
     * @return the ID of the book to select
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the ID of the book to select
     */
    public void setId(Long id) {
        this.id = id;
    }
}
