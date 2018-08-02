package com.dmko.conferencemanagementsystem.utils;


import com.google.gson.annotations.SerializedName;

public class ApiError {

    @SerializedName("error_code")
    private int errorCode;

    private String description;

    public ApiError(int errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "errorCode=" + errorCode +
                ", description='" + description + '\'' +
                '}';
    }
}
