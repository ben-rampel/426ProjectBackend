package com.brampel.tdserver.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sid;

    @Basic
    private long score;
    @Basic
    private Instant timestamp;

    @ManyToOne()
    private User user;

    public Score(long score, Instant timestamp, User user) {
        this.score = score;
        this.timestamp = timestamp;
        this.user = user;
    }

    protected Score() {

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
