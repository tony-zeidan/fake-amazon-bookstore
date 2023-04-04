package fakeamazon.bookstore.demo.input.templates;

public class CustomerUsernameTemplate {
    String username;

    public CustomerUsernameTemplate(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
