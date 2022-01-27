package com.strayge;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

class AlertManagerPayload {
    @JsonProperty("labels")
    public Map<String, Object> labels;

    @JsonProperty("annotations")
    public Map<String, Object> annotations;

    @JsonProperty("generatorURL")
    public String generatorURL;

    @JsonProperty("startsAt")
    public String startsAt;

    @JsonProperty("endsAt")
    public String endsAt;
}
