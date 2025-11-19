package org.openapitools.service;

import org.openapitools.domain.User;
import org.openapitools.domain.UserCreator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class UserService {
    
    private final Map<Integer, User> users;
    private final UserCreator userCreator;

    public UserService() {
        this.users = new ConcurrentHashMap<>();
        this.userCreator = new UserCreator();
        initializeDefaultUsers();
    }

    private void initializeDefaultUsers() {
        // Créer quelques utilisateurs par défaut pour les tests
        User admin = userCreator.createUser("Admin", "admin@uqac.ca", "ADMIN");
        User dev = userCreator.createUser("Développeur1", "dev1@uqac.ca", "DEVELOPER");
        User user = userCreator.createUser("Utilisateur1", "user1@uqac.ca", "USER");
        
        users.put(admin.getUserID(), admin);
        users.put(dev.getUserID(), dev);
        users.put(user.getUserID(), user);
    }

    public User createUser(String name, String email, String role) {
        User user = userCreator.createUser(name, email, role);
        users.put(user.getUserID(), user);
        return user;
    }

    public User getUserById(int userId) {
        return users.get(userId);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public User updateUser(int userId, String name, String email, String role) {
        User user = users.get(userId);
        if (user == null) {
            return null;
        }

        if (name != null && !name.trim().isEmpty()) {
            user.setName(name);
        }
        if (email != null && !email.trim().isEmpty()) {
            user.setEmail(email);
        }
        if (role != null && !role.trim().isEmpty()) {
            user.setRole(role);
        }

        return user;
    }

    public boolean deleteUser(int userId) {
        User user = users.remove(userId);
        return user != null;
    }

    public boolean userExists(int userId) {
        return users.containsKey(userId);
    }
}

