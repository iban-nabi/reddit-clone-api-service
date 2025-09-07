package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.dtos.request.ChangePasswordRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UserRequest;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponse getUser(String username){
        return userMapper.toDtoResponse(userRepository.findByUsername(username)) ;
    }

    public List<UserResponse> searchUsers(String query){
        return userRepository.searchUsers(query)
                .stream()
                .map(userMapper::toDtoResponse)
                .toList();
    }

    public boolean createUser(UserRequest userRequest) {
        if(userRepository.existByUsername(userRequest.getUsername())){
            return false;
        }

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .birthday(LocalDate.now())
                .archived(false)
                .build();

        userRepository.save(user);
        return true;
    }

    public boolean updatePassword(String username,
                                  ChangePasswordRequest changePasswordRequest) {
        if(userRepository.existByUsername(username)){
            userRepository.updatePassword(username, changePasswordRequest);
            return true;
        }
        return false;
    }

    public boolean deleteUser(String username) {
        if(userRepository.existByUsername(username)){
            userRepository.delete(username);
            return true;
        }
        return false;
    }
}
