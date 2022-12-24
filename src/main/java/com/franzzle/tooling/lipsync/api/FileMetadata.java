package com.franzzle.tooling.lipsync.api;

public class FileMetadata {
    private String uuid;

    public FileMetadata(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
