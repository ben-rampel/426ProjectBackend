package com.brampel.tdserver.api;

public class UserUpdateRequest {
    String username;
    String password;
    String avatarURL;

    public UserUpdateRequest(String username, String password, String avatarURL) {
        this.username = username;
        this.password = password;
        this.avatarURL = avatarURL;
    }

    public UserUpdateRequest() {
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

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
}
