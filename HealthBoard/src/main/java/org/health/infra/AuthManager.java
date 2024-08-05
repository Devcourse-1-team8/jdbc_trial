package org.health.infra;

public class AuthManager {
    private static AuthManager instance = new AuthManager();

    private Integer userId;

    private AuthManager() {}

    public static AuthManager getInstance() {
        return instance;
    }

    public void login(int userId) {
        this.userId = userId;
    }

    public void logout() {
        this.userId = null;
    }

    public int getCurrentUser() {
        if (userId == null) {
            throw new RuntimeException("No User Login");
        }

        return userId;
    }
}
