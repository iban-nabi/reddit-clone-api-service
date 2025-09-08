package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.auth.AuthenticationContextHelper;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.ChangePasswordRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UserRequest;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.exceptions.ResourceNotFoundException;
import com.parallelquantumcorp.redditcloneapiservice.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AuthenticationContextHelper contextHelper;

    public UserResponse getUser(String username) throws ResourceNotFoundException{
        User user = userRepository.findByUsername(username);

        if(user==null || user.isArchived()){
            throw new ResourceNotFoundException("User does not exist");
        }

        return userMapper.toDtoResponse(user) ;
    }

    public List<UserResponse> searchUsers(String query){
        return userRepository.searchUsers(query)
                .stream()
                .filter(user -> !user.isArchived())
                .map(userMapper::toDtoResponse)
                .toList();
    }

    public void createUser(UserRequest userRequest) throws IllegalStateException {
        if (userRepository.existByUsername(userRequest.getUsername())) {
            throw new IllegalStateException("Username '" + userRequest.getUsername() + "' already exists");
        }

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .birthday(LocalDate.now())
                .archived(false)
                .build();

        userRepository.save(user);
    }

    public void updatePassword(ChangePasswordRequest changePasswordRequest) throws ResourceNotFoundException, AuthenticationException {
        String username = contextHelper.getNameFromAuthToken();
        User user = userRepository.findByUsername(username);

        if (user.isArchived()) {
            throw new ResourceNotFoundException("Cannot update password for archived user");
        }

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new AuthenticationException("Old password does not match");
        }

        userRepository.updatePassword(username, passwordEncoder.encode(changePasswordRequest.getNewPassword()));
    }

    public void deleteUser() throws ResourceNotFoundException {
        String username = contextHelper.getNameFromAuthToken();
        User user = userRepository.findByUsername(username);

        if (user.isArchived()) {
            throw new ResourceNotFoundException("Cannot delete archived user");
        }

        userRepository.delete(username);
    }
}
