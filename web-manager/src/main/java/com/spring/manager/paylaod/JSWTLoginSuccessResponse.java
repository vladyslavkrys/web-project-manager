package com.spring.manager.paylaod;

public class JSWTLoginSuccessResponse {
    private boolean isSuccess;
    private String token;

    public JSWTLoginSuccessResponse(boolean isSuccess, String token) {
        this.isSuccess = isSuccess;
        this.token = token;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "JSWTLoginSuccessResponse{" +
                "isSuccess=" + isSuccess +
                ", token='" + token + '\'' +
                '}';
    }
}
