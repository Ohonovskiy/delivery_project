package application.pet.delivery.config.security;

import application.pet.delivery.security.UserDetailsServiceImpl;
import application.pet.delivery.security.handlers.CustomAccessDeniedHandler;
import application.pet.delivery.security.passwordEncoders.NoEncode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration class for Spring Security settings and authentication mechanisms.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringConfig {

    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Constructs a new instance of SpringConfig with the provided user details service.
     *
     * @param userDetailsService An implementation of the UserDetailsServiceImpl to retrieve user details.
     */
    @Autowired
    public SpringConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
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
        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .requestMatchers(HttpMethod.POST).authenticated()
                        .requestMatchers(
                                "/auth/**",
                                "/css/**",
                                "/images/**",
                                "/img/**",
                                "/lib/**",
                                "/js/**",
                                "/scss/**").permitAll()  // Permit all static resources
                        .anyRequest().authenticated())  // Require authentication for any other requests
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(ses -> ses
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation().migrateSession()
                        .invalidSessionUrl("/auth/login")
                        .maximumSessions(1)
                        .expiredUrl("/auth/login?expired=true"))
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .permitAll()
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/auth/login?error=true"))
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/auth/login"))
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedPage("/denied")
                        .accessDeniedHandler(new CustomAccessDeniedHandler()))
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
}

