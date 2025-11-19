package org.openapitools.domain;

public class UserCreator {
    private int nextUserID;

    public UserCreator() {
        this.nextUserID = 1;
    }

    public UserCreator(int startingID) {
        this.nextUserID = startingID;
    }

    public User createUser(String name, String email) {
        return createUser(name, email, "USER");
    }

    public User createUser(String name, String email, String role) {
        int userID = generateUserID();
        
        User user = new User(userID, name, email, role);

        System.out.println("Utilisateur créé avec succès: " + name + " (ID: " + userID + ", Rôle: " + role + ")");
        return user;
    }

    public int generateUserID() {
        return nextUserID++;
    }

    public int getNextUserID() {
        return nextUserID;
    }

    public void setNextUserID(int nextUserID) {
        this.nextUserID = nextUserID;
    }
}

