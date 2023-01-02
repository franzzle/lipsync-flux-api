package com.franzzle.tooling.lipsync.api.service.model;

import java.io.Serializable;

public class Mouthcue implements Serializable {
    private Float start;
    private Float end;
    private String value;

    public Float getStart() {
        return start;
    }

    public void setStart(Float start) {
        this.start = start;
    }

    public Float getEnd() {
        return end;
    }

    public void setEnd(Float end) {
        this.end = end;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
