package com.franzzle.tooling.lipsync.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.franzzle.tooling.lipsync.api.config.ConvertableListConverter;

import java.util.List;

public class Convertables {
    @JsonSerialize(converter = ConvertableListConverter.class)
    public List<Convertable> convertables;

    public List<Convertable> getConvertables() {
        return convertables;
    }

    public void setConvertables(List<Convertable> convertables) {
        this.convertables = convertables;
    }
}
