package com.franzzle.tooling.lipsync.api.service.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgressLog {
    /**
     * Can be 'Trace', 'Info'
     */
    private String level;

    /**
     * Contains messages like : 'Progress: 68%' when type equals 'progress'
     * Starts with : 'Application startup' when type equals 'start'
     * Starts with : 'Application terminating with error' when type equals 'failure'
     */
    private String message;
}
