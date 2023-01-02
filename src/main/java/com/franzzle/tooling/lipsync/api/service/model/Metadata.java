package com.franzzle.tooling.lipsync.api.service.model;

import java.io.Serializable;

public class Metadata implements Serializable {
    private String soundFile;
    private Float duration;

    public String getSoundFile() {
        return soundFile;
    }

    public void setSoundFile(String soundFile) {
        this.soundFile = soundFile;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }
}
