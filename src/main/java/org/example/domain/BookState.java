package org.example.domain;

import java.util.Arrays;

public enum BookState {
    POSSIBLE("대여가능") {
        public void showState() {
            System.out.println("[System] 반납된 도서입니다.");
        }

        public void showChangeState(){
            System.out.println("[System] 도서가 대여 처리 되었습니다.");
        }
    },
    RENTING("대여중") {
        public void showChangeState()  {
            System.out.println("[System] 도서가 반납 처리 되었습니다.");
        }
        public void showState(){
            System.out.println("[System] 대여중인 도서입니다.");
        }
    },
    ORGANIZING("정리중"){
        public void showState() {
            System.out.println("[System] 정리중인 도서입니다.");
        }
        public void showChangeState(){
            System.out.println("[System] 정리중인 도서입니다.");
        }
    },
    LOST("분실"){
        public void showState() {
            System.out.println("[System] 분실 처리된 도서입니다.");
        }
        public void showChangeState(){
            System.out.println("[System] 도서가 분실 처리 되었습니다.");
        }
    };

    private final String value;

    private BookState(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public abstract void showState();
    public abstract void showChangeState();
}
