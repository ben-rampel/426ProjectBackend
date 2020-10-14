package com.brampel.tdserver.api;

import com.brampel.tdserver.model.User;

import java.util.List;

public class PublicUser {
    long id;
    String username;
    String avatarURL;
    List<PublicScore> scores;

    public PublicUser(User user, List<PublicScore> scores) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatarURL = user.getAvatarURL();
        this.scores = scores;
    }

    public PublicUser(long id, String username, String avatarURL, List<PublicScore> scores) {
        this.id = id;
        this.username = username;
        this.avatarURL = avatarURL;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public List<PublicScore> getScores() {
        return scores;
    }

    public void setScores(List<PublicScore> scores) {
        this.scores = scores;
    }
}
