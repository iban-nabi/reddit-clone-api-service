package com.parallelquantumcorp.redditcloneapiservice.config;

import com.parallelquantumcorp.redditcloneapiservice.auth.InMemoryUserAuthenticationProvider;
import com.parallelquantumcorp.redditcloneapiservice.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures the security filter chain for HTTP requests.
     * 
     * This configuration:
     * - Sets the session management to stateless
     * - Disables CSRF protection
     * - Permits public access to registration and login endpoints
     * - Requires authentication for all other requests
     * - Adds JWT authentication filter before the default authentication filter
     *
     * @param http The HttpSecurity object to configure
     * @return The built SecurityFilterChain
     * @throws Exception If there is an error configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .sessionManagement( c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( c -> c
                        .requestMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Creates and configures the AuthenticationManager bean for Spring Security.
     * 
     * @param authenticationProvider The in-memory authentication provider to be used for user authentication
     * @return A configured ProviderManager instance that will handle authentication requests
     */
    @Bean
    public AuthenticationManager authenticationManager(InMemoryUserAuthenticationProvider authenticationProvider){
        return new ProviderManager(authenticationProvider);
    }

    /**
     * Creates and configures a password encoder bean for secure password hashing.
     * 
     * @return BCryptPasswordEncoder instance that implements PasswordEncoder interface
     *         to provide BCrypt password hashing functionality
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
