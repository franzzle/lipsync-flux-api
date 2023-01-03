package com.franzzle.tooling.lipsync.api.service.model;

//TODO Use
public enum ProgressType {
    START("start"),
    PROGRESS("progress"),
    SUCCESS("success"),
    FAILURE("failure"),
    LOG("log");

    private String typedValue;

    ProgressType(String typedValue) {
        this.typedValue = typedValue;
    }
}
