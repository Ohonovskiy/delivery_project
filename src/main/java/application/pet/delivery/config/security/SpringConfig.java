package application.pet.delivery.config.security;

import application.pet.delivery.security.DeliverymanDetailsServiceImpl;
import application.pet.delivery.security.UserDetailsServiceImpl;
import application.pet.delivery.security.passwordEncoders.NoEncode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Spring Security settings and authentication mechanisms.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private final DeliverymanDetailsServiceImpl deliverymanDetailsService;

    /**
     * Constructs a new instance of SpringConfig with the provided user details service.
     *
     * @param userDetailsService        An implementation of the UserDetailsServiceImpl to retrieve user details.
     * @param deliverymanDetailsService An implementation of the UserDetailsServiceImpl to retrieve user details.
     */
    @Autowired
    public SpringConfig(UserDetailsServiceImpl userDetailsService, DeliverymanDetailsServiceImpl deliverymanDetailsService) {
        this.userDetailsService = userDetailsService;
        this.deliverymanDetailsService = deliverymanDetailsService;
    }


    /**
     * Configures the security filter chain with authentication and authorization settings.
     *
     * @param http The HttpSecurity instance to configure.
     * @return A SecurityFilterChain instance with the configured security settings.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/auth/**", "/css/**", "/images/**").permitAll()
                                .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(ses ->
                        ses.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                .sessionFixation().migrateSession()
                                .invalidSessionUrl("/auth/login")
                                .maximumSessions(1)
                                .expiredUrl("/auth/login?expired=true"))
                .formLogin(form -> form.loginPage("/auth/login")
                        .permitAll()
                        .defaultSuccessUrl("/")
                        .failureUrl("/auth/login?error=true"))
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/auth/login"))
                .authenticationProvider(daoAuthenticationProvider())
                .build();
    }

    /**
     * Creates a PasswordEncoder bean for securely encoding and decoding passwords.
     *
     * @return A BCryptPasswordEncoder instance with strength 12.
     */
    @Bean
    protected PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder(12);
        return new NoEncode();
    }

    /**
     * Creates a DaoAuthenticationProvider bean to authenticate users based on user details service and password encoder.
     *
     * @return A DaoAuthenticationProvider instance configured with user details service and password encoder.
     */
    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
    @Bean
    protected DaoAuthenticationProvider deliveryManAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(deliverymanDetailsService);
        return daoAuthenticationProvider;
    }
}

