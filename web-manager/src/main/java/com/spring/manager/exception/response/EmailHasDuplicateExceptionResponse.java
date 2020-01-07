package com.spring.manager.exception.response;

public class EmailHasDuplicateExceptionResponse {
    private String email;

    public EmailHasDuplicateExceptionResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
