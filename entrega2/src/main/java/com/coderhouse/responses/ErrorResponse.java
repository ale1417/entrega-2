package com.coderhouse.responses;

public class ErrorResponse {

    private String message;
    private String detail;

   
    public ErrorResponse() {
    }

    
    public ErrorResponse(String message, String detail) {
        this.message = message;
        this.detail = detail;
    }

    // Getters y setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
