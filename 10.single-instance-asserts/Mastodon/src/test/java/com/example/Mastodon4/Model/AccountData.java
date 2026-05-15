package com.example.Mastodon4.Model;

public class AccountData {
    private final String email;
    private final String username;
    private final String password;


    public AccountData(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
