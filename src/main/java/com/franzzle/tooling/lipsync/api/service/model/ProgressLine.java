package com.franzzle.tooling.lipsync.api.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgressLine {
    /**
     * Can be 'start', 'progress', 'success', 'failure', 'log'
     */
    private String type;

    /**
     * If type equals to 'start' this contains a full filepath
     * (although we are also able to redefine this property to only the wav filename)
     */
    private String file;

    /**
     * Only has a value in case of type equals to 'progress'.
     */
    private Float value;

    /**
     * Can contain such a reason 'Error processing file \"no-such-file.wav\' in case type equals to 'failure'.
     */
    private String reason;

    /**
     * Log that can provide extended info
     */
    private ProgressLog log;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ProgressLog getLog() {
        return log;
    }

    public void setLog(ProgressLog log) {
        this.log = log;
    }
}
