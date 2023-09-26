package com.library.java_library_management.response;

public record ApiResponse(
        ApiStatus status,
        String message
) {
    public static ApiResponse success(String message) {return new ApiResponse(ApiStatus.SUCCESS, message);}

    public static ApiResponse error(String message) {
        return new ApiResponse(ApiStatus.ERROR, message);
    }
}