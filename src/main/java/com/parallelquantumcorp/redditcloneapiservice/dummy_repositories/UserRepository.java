package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.dtos.UserRequest;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepository {
    private final Map<String, User> users = new LinkedHashMap<>();

    public User findByUsername(String username) {
        User user = users.get(username);
        if(user != null && !user.isArchived()){
            return user;
        }
        return null;
    }

    public List<User> searchUsers(String username) {
        return users.values()
                .stream()
                .filter(user -> !user.isArchived()
                        && user.getUsername().toLowerCase()
                        .contains(username.toLowerCase()))
                .toList();
    }

    public boolean save(User user){
        if(!users.containsKey(user.getUsername())){
            Long id = users.size()+1L;
            user.setId(id);
            users.put(user.getUsername(), user);
            return true;
        }
        return false;
    }

    public boolean updatePassword(UserRequest userRequest){
        User user = users.get(userRequest.getUsername());
        if(user != null && !user.isArchived()){
            user.setPassword(userRequest.getPassword());
            users.put(user.getUsername(), user);
            return true;
        }
        return false;
    }

    public boolean delete(String username){
        User user = users.get(username);
        if(user != null && !user.isArchived()){
            users.get(username).setArchived(true);
            return true;
        }
        return false;
    }
}
