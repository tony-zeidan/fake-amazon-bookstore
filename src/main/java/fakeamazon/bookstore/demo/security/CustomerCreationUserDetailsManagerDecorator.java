package fakeamazon.bookstore.demo.security;

import fakeamazon.bookstore.demo.model.Customer;
import fakeamazon.bookstore.demo.services.CustomerDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

/**
 * A decorator for the {@link UserDetailsManager} bean registered for the application,
 * designed to transparently create associated {@link Customer} records when a new
 * {@link UserDetails} is registered in the application.
 */
public class CustomerCreationUserDetailsManagerDecorator implements UserDetailsManager {

    private final UserDetailsManager manager;

    private final CustomerDetailsService customerService;

    /**
     * @param manager the core interface for loading user-specific data
     * @param customerService the service used to manage customer details
     */
    public CustomerCreationUserDetailsManagerDecorator(final UserDetailsManager manager, final CustomerDetailsService customerService) {
        this.manager = manager;
        this.customerService = customerService;
    }

    /**
     * Create a new user with the supplied details.
     *
     * @param user the model of the user to add to the system
     */
    @Override
    public void createUser(UserDetails user) {
        this.customerService.makeCustomer(user.getUsername());
        this.manager.createUser(user);
    }

    /**
     * Update the specified user.
     *
     * @param user the model of the user to update
     */
    @Override
    public void updateUser(UserDetails user) {
        this.manager.updateUser(user);
    }

    /**
     * Remove the user with the given login name from the system.
     *
     * @param username the username of the user to delete
     */
    @Override
    public void deleteUser(String username) {
        this.manager.deleteUser(username);
    }

    /**
     * Modify the current user's password. This should change the user's password in the
     * persistent user repository (database, LDAP etc).
     *
     * @param oldPassword current password (for re-authentication if required)
     * @param newPassword the password to change to
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        this.manager.changePassword(oldPassword, newPassword);
    }

    /**
     * Check if a user with the supplied login name exists in the system.
     *
     * @param username the username of the user to check the presence of
     * @return whether a user with the given username exists
     */
    @Override
    public boolean userExists(String username) {
        return this.manager.userExists(username);
    }

    /**
     * @param username the username identifying the user whose data is required.
     * @return the {@link UserDetails} associated with the given username
     * @throws UsernameNotFoundException when a user with the given username does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.manager.loadUserByUsername(username);
    }
}
