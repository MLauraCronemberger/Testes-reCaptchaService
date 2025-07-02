package com.example.demo;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CaptchaResponse {
	
    public boolean success;

    @JsonProperty("challenge_ts")
    public String challengeTs;

    public String hostname;

    public Float score;

    public String action;

    @JsonProperty("error-codes")
    public String[] errorCodes;

    @Override
    public String toString() {
        return "CaptchaResponse{" +
                "success=" + success +
                ", challengeTs='" + challengeTs + '\'' +
                ", hostname='" + hostname + '\'' +
                ", score=" + score +
                ", action='" + action + '\'' +
                ", errorCodes=" + Arrays.toString(errorCodes) +
                '}';
    }

}
