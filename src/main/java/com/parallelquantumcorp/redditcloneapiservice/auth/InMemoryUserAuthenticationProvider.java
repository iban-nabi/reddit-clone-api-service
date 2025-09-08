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
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new BadCredentialsException("User not Found");
        }

        if(!passwordEncoder.matches(authentication.getCredentials().toString(),
                user.getPassword())){
            throw new BadCredentialsException("Wrong Password");
        }

        return new UsernamePasswordAuthenticationToken(username,password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
