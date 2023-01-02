package com.franzzle.tooling.lipsync.api.service.model;

public class RhubarbDTO {
    private String sourceUuid;
    private String sourceInputPath;

    private String destinationOuputPath;

    private String spokenTextHint;

    public String getSourceUuid() {
        return sourceUuid;
    }

    public void setSourceUuid(String sourceInputFile) {
        this.sourceUuid = sourceInputFile;
    }

    public String getSourceInputPath() {
        return sourceInputPath;
    }

    public void setSourceInputPath(String sourceInputPath) {
        this.sourceInputPath = sourceInputPath;
    }

    public String getDestinationOuputPath() {
        return destinationOuputPath;
    }

    public void setDestinationOuputPath(String destinationOuputPath) {
        this.destinationOuputPath = destinationOuputPath;
    }

    public String getSpokenTextHint() {
        return spokenTextHint;
    }

    public void setSpokenTextHint(String spokenTextHint) {
        this.spokenTextHint = spokenTextHint;
    }
}
