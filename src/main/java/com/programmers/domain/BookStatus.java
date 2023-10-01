package com.programmers.domain;

public enum BookStatus {
    AVAILABLE("대여 가능"), ONGOING("대여중"), PREPARING("도서 정리중"), LOST("분실됨"), DELETED("삭제됨");

    private final String detailStatus;

    BookStatus(String detailStatus){
        this.detailStatus = detailStatus;
    }

    public String getDetailStatus() {
        return detailStatus;
    }

}
