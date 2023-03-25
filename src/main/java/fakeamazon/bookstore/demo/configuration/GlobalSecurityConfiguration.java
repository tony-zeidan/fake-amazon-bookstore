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

@Configuration
public class GlobalSecurityConfiguration {

    private final CustomerDetailsService customerDetailsService;

    public GlobalSecurityConfiguration(final CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(withDefaults()) /* Route /login to a boilerplate login page */
            .logout()
                .logoutSuccessUrl("/");
        http.authorizeHttpRequests()
                .requestMatchers("/bookstore")
                    .authenticated()
                .requestMatchers("/bookstore/**")
                    .authenticated()
                .requestMatchers("/css/**", "/js/**")
                    .permitAll()
                .requestMatchers("/actuator")
                    .permitAll()
                .requestMatchers("/actuator/**")
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
                    .hasRole("USER");
        http.csrf()
                .disable();
        return http.build();
    }

    @Bean
    UserDetailsManager users(DataSource dataSource) {
        UserDetails user = User.builder()
                .username("user")
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
        users.createUser(admin);

        return users;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}