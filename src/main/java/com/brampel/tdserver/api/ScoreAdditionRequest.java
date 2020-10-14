package com.brampel.tdserver.api;

import java.io.Serializable;

public class ScoreAdditionRequest implements Serializable {
    private long score;
    private long id;

    public ScoreAdditionRequest(long score, long id) {
        this.score = score;
        this.id = id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
