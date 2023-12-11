package com.franzzle.tooling.lipsync.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Convertable {
    private String uuid;
    private String text;
    private String originalFileName;


    public void setUuid(String uuid) {
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

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    // Static factory method to create Convertable from uuid
    public static Convertable fromUuid(String uuid) {
        Convertable convertable = new Convertable();
        convertable.setUuid(uuid);
        return convertable;
    }
}
