package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.dto.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    private final UserDetailsManager userDetailsManager;

    private final PasswordEncoder passwordEncoder;

    public UserService(final UserDetailsManager userDetailsManager, final PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

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
