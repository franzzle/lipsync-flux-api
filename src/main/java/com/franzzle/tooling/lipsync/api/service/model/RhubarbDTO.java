package com.franzzle.tooling.lipsync.api.service.model;

public class RhubarbDTO {
    private String sourceInputFile;
    private String sourceInputPath;

    private String destFile;
    private String destPath;

    private String spokenTextHint;

    public String getSourceInputFile() {
        return sourceInputFile;
    }

    public void setSourceInputFile(String sourceInputFile) {
        this.sourceInputFile = sourceInputFile;
    }

    public String getSourceInputPath() {
        return sourceInputPath;
    }

    public void setSourceInputPath(String sourceInputPath) {
        this.sourceInputPath = sourceInputPath;
    }

    public String getDestFile() {
        return destFile;
    }

    public void setDestFile(String destFile) {
        this.destFile = destFile;
    }

    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    public String getSpokenTextHint() {
        return spokenTextHint;
    }

    public void setSpokenTextHint(String spokenTextHint) {
        this.spokenTextHint = spokenTextHint;
    }
}
