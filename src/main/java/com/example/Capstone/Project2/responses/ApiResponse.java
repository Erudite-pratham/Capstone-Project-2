package com.example.Capstone.Project2.responses;

import lombok.Getter;

@Getter
public class ApiResponse {
    private final String message;
    private final boolean success;
    private final Object data;

    public ApiResponse(String message, boolean success, Object data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

}
