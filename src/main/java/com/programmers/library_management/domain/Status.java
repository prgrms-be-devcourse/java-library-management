package com.programmers.library_management.domain;

public enum Status {
    Available("대여 가능"), Ranted("대여중"), Lost("분실"), Organized("정리중");
    private final String statusMessage;
    Status(String statusMessage){
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return this.statusMessage;
    }
}
