package fakeamazon.bookstore.demo.configuration;

import fakeamazon.bookstore.demo.security.CustomerCreationUserDetailsManagerDecorator;
import fakeamazon.bookstore.demo.services.CustomerDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * The configuration class used by the application to manage endpoint-wide security concerns.
 * This includes authentication restrictions for different endpoints, and configuring the schema
 * of user creation and retrieval.
 */
@Configuration
public class GlobalSecurityConfiguration {

    private final CustomerDetailsService customerDetailsService;

    /**
     * @param customerDetailsService the service used by the application to fetch customer details.
     */
    public GlobalSecurityConfiguration(final CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    /**
     * Configures our application endpoint security rules, based on users being anonymous (guests),
     * users, or administrators.
     *
     * @param http the configuration for web-based security for specific http requests
     * @return the set of filters which are capable of being matched against our HttpServletRequests,
     *         in order to decide whether a given filter applies to that request
     * @throws Exception configuring HTTP requests can throw arbitrary Exception instances
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(withDefaults()) /* Route /login to a boilerplate login page */
            .logout()
                .logoutSuccessUrl("/");
        http.authorizeHttpRequests()
                .requestMatchers("/bookstore/stopRepo")
                    .permitAll()
                .requestMatchers("/bookstore")
                    .authenticated()
                .requestMatchers("/bookstore/**")
                    .authenticated()
                .requestMatchers("/css/**", "/js/**")
                    .permitAll()
                .requestMatchers("/")
                    .permitAll()
                .requestMatchers("/error")
                    .permitAll()
                .requestMatchers("/user/registration")
                    .permitAll()
                .requestMatchers("/user")
                    .hasRole("USER")
                .requestMatchers("/user/**")
                    .hasRole("USER")
                .requestMatchers("/owner")
                    .hasRole("ADMIN")
                .requestMatchers("/owner/**")
                    .hasRole("ADMIN")
                .requestMatchers("/owneractions/**")
                    .hasRole("ADMIN")
                .requestMatchers("/useractions/**")
                    .hasRole("USER")
                .requestMatchers("/api-docs", "/swagger-ui/**", "/v3/**")
                    .authenticated();
        http.csrf()
                .disable();
        return http.build();
    }

    /**
     * Configures our initial set of application users, for administrative access and testing.
     *
     * @param dataSource the datasource used by the application; @see {@link DatabaseConfiguration#dataSource()}
     * @return the core interface for loading user-specific data
     */
    @Bean
    UserDetailsManager users(DataSource dataSource) {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails user1 = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails userNick = User.builder()
                .username("Nick")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("USER", "ADMIN")
                .build();
        UserDetailsManager users = new CustomerCreationUserDetailsManagerDecorator(new JdbcUserDetailsManager(dataSource), this.customerDetailsService);
        users.createUser(user);
        users.createUser(user1);
        users.createUser(userNick);
        users.createUser(admin);

        return users;
    }

    /**
     * Configures our password encoding strategy for new user password storage.
     *
     * @return the service interface used by the application for encoding passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring does not expose information regarding the identity of a logged-in user in thymeleaf templates
     * by default, but by registering a custom thymeleaf dialect bean for spring security, this information
     * can be made available during view rendering.
     *
     * @return the thymeleaf dialect used for Spring Security integration
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}