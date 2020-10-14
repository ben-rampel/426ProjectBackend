package com.brampel.tdserver.api;

import java.io.Serializable;

public class GenericResponse implements Serializable {
    private String info;
    private String error;

    public GenericResponse(String info, String error) {
        this.info = info;
        this.error = error;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
