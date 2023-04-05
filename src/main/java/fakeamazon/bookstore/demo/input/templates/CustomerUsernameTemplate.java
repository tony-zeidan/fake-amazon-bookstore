package fakeamazon.bookstore.demo.input.templates;

/**
 * A template class for creating a simplified Customer object with a specified username.
 */
public class CustomerUsernameTemplate {
    String username;

    /**
     * Constructs a new CustomerUsernameTemplate with the specified username.
     *
     * @param username the username for the new Customer object
     */

    public CustomerUsernameTemplate(String username) {
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
     * set the username for the Customer object.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
