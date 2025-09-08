package com.parallelquantumcorp.redditcloneapiservice.auth;

import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class InMemoryUserAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * Authenticates a user based on their credentials.
     * 
     * @param authentication The authentication object containing user credentials
     * @return A UsernamePasswordAuthenticationToken if authentication is successful
     * @throws BadCredentialsException if the user is not found, archived, or if the password is incorrect
     * @throws AuthenticationException if there are other authentication-related issues
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByUsername(username);

        if(user==null || user.isArchived()){
            throw new BadCredentialsException("User not Found");
        }

        if(!passwordEncoder.matches(authentication.getCredentials().toString(),
                user.getPassword())){
            throw new BadCredentialsException("Wrong Password");
        }

        return new UsernamePasswordAuthenticationToken(username,password, Collections.emptyList());
    }

    /**
     * Determines if this authentication provider supports the given authentication type.
     * 
     * @param authentication the Class to check
     * @return true if the authentication type is supported (specifically, if it's assignable from UsernamePasswordAuthenticationToken),
     *         false otherwise
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
