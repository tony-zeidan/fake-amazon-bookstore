package fakeamazon.bookstore.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

@TestConfiguration
public class TestSetup {

    @Autowired
    private UserDetailsManager detailsManager;

    @Autowired
    private PasswordEncoder encoder;

    @EventListener(ApplicationReadyEvent.class)
    public void setup() {

        UserDetails user222 = User.builder()
                .username("user222")
                .password(encoder.encode("user222"))
                .roles("USER")
                .build();

        UserDetails user223 = User.builder()
                .username("user223")
                .password(encoder.encode("user223"))
                .roles("USER")
                .build();

        UserDetails user224 = User.builder()
                .username("user224")
                .password(encoder.encode("user224"))
                .roles("USER")
                .build();

        UserDetails admin222 = User.builder()
                .username("admin222")
                .password(encoder.encode("admin224"))
                .roles("ADMIN")
                .build();

        UserDetails admin223 = User.builder()
                .username("admin223")
                .password(encoder.encode("admin224"))
                .roles("ADMIN")
                .build();

        UserDetails admin224 = User.builder()
                .username("admin224")
                .password(encoder.encode("admin224"))
                .roles("ADMIN")
                .build();

        detailsManager.createUser(user222);
        detailsManager.createUser(user223);
        detailsManager.createUser(user224);
        detailsManager.createUser(admin222);
        detailsManager.createUser(admin223);
        detailsManager.createUser(admin224);
    }
}