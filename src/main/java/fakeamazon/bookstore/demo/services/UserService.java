package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.aop.LoggedServiceOperation;
import fakeamazon.bookstore.demo.dto.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

/**
 * The service used by the application to manage the registration of new users and customers.
 */
@Service
@Transactional
public class UserService {
    private final UserDetailsManager userDetailsManager;

    private final PasswordEncoder passwordEncoder;

    /**
     * @param userDetailsManager the core interface for loading user-specific data
     * @param passwordEncoder the service interface used by the application for encoding passwords
     */
    public UserService(final UserDetailsManager userDetailsManager, final PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register a new user and customer record in the application based on the content of the
     * provided Data Transfer Object (DTO). Basic integrity checks are performed on the content
     * of the DTO to ensure that the provided passwords match, and that duplicate users
     * are not created in the system.
     * All users created through this endpoint will belong to the user group "USER".
     *
     * @param userDto the DTO used to populate the details of a newly-created
     *                user instance in the database
     * @return the committed details of the newly-created user
     */
    @LoggedServiceOperation
    public UserDetails registerNewUser(final UserDTO userDto) {
        if (this.userDetailsManager.userExists(userDto.getUsername())) {
            throw new IllegalArgumentException("There is already an account with the username " + userDto.getUsername() + "!");
        }
        if (!userDto.getPassword().equals(userDto.getMatchingPassword())) {
            throw new IllegalArgumentException("The passwords you have entered do not match!");
        }

        final UserDetails user = User.builder()
                .username(userDto.getUsername())
                .password(this.passwordEncoder.encode(userDto.getPassword()))
                .roles("USER")
                .build();
        this.userDetailsManager.createUser(user);
        return user;
    }
}
