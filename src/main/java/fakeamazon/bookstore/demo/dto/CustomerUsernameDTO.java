package fakeamazon.bookstore.demo.dto;

import fakeamazon.bookstore.demo.controller.rest.BookOwnerRestController;

/**
 * The Data Transfer Object (DTO) used to query a simplified Customer object with a specified username;
 *
 * @see BookOwnerRestController#userList()
 */
public class CustomerUsernameDTO {
    String username;

    /**
     * Constructs a new CustomerUsernameTemplate with the specified username.
     *
     * @param username the username for the new Customer object
     */

    public CustomerUsernameDTO(String username) {
        this.username = username;
    }

    /**
     * Returns the username for the Customer object.
     *
     * @return the username for the Customer object
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username for the customer object
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
