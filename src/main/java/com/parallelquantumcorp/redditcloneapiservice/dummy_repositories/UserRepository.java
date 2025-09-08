package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for managing user data in memory using a Map-based storage.
 * This class provides basic CRUD operations and search functionality for User entities.
 * Uses soft delete through an archived flag instead of physical deletion.
 */
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

    public void save(User user){
        Long id = users.size()+1L;
        user.setId(id);
        users.put(user.getUsername(), user);
    }

    public void updatePassword(String username, String password){
        users.get(username).setPassword(password);
    }

    public void delete(String username){
        users.get(username).setArchived(true);
    }

    public boolean existByUsername(String username){
        return users.containsKey(username);
    }
}
