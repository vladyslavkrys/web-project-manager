package com.spring.manager.exception.response;

public class InvalidLoginResponse {
    private String username;
    private String password;

    public InvalidLoginResponse() {
        username = "Username is not valid";
        password = "Password is not valid";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
