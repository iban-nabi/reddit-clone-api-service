package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.dtos.UserRequest;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class UserRepository {
    private final Map<String, User> users = new LinkedHashMap<>();

    public User findByUsername(String username) {
        User user = users.get(username);
        if(user != null && !user.isArchived()){
            return user;
        }
        return users.get(username);
    }

    public User save(User user){
        if(!users.containsKey(user.getPassword())){
            Long id = users.size()+1L;
            user.setId(id);
            users.put(user.getUsername(), user);
        }
        return null;
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
