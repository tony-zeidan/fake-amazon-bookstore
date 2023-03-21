package fakeamazon.bookstore.demo.security;

import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.services.CustomerDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

public class CustomerCreationUserDetailsManagerDecorator implements UserDetailsManager {

    private final UserDetailsManager manager;

    private final CustomerDetailsService customerService;

    public CustomerCreationUserDetailsManagerDecorator(final UserDetailsManager manager, final CustomerDetailsService customerService) {
        this.manager = manager;
        this.customerService = customerService;
    }

    @Override
    public void createUser(UserDetails user) {
        this.customerService.makeCustomer(user.getUsername());
        this.manager.createUser(user);
    }

    @Override
    public void updateUser(UserDetails user) {
        this.manager.updateUser(user);
    }

    @Override
    public void deleteUser(String username) {
        this.manager.deleteUser(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        this.manager.changePassword(oldPassword, newPassword);
    }

    @Override
    public boolean userExists(String username) {
        return this.manager.userExists(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.manager.loadUserByUsername(username);
    }
}
