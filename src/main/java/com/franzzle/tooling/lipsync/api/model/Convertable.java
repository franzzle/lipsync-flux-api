package com.franzzle.tooling.lipsync.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Convertable {
    private final String uuid;
    private String text;

    public Convertable(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @JsonIgnore
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
