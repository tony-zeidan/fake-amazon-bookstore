package fakeamazon.bookstore.demo.dto;

/**
 * The Data Transfer Object (DTO) used to populate the details of a newly-created user instance in
 * the database; @see {@link fakeamazon.bookstore.demo.services.UserService#registerNewUser(UserDTO)}
 */
public class UserDTO {
    private String username;

    private String password;

    private String matchingPassword;

    /**
     * @return the username of the user to be created
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username of the user to be created
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password of the user to be created
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password of the user to be created
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the matching password of the user to be created
     */
    public String getMatchingPassword() {
        return matchingPassword;
    }

    /**
     * @param matchingPassword the matching password of the user to be created
     */
    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
