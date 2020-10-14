package com.brampel.tdserver.api;

import com.brampel.tdserver.model.Score;

import java.time.Instant;

public class PublicScore implements Comparable<PublicScore>{
    long sid;
    long score;
    String username;
    Instant timestamp;

    public PublicScore(Score s) {
        this.sid = s.getSid();
        this.score = s.getScore();
        this.timestamp = s.getTimestamp();
        this.username = s.getUser().getUsername();
    }

    @Override
    public int compareTo(PublicScore publicScore) {
        return (int) (publicScore.score - this.score);
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
