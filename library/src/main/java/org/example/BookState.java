package org.example;

public enum BookState {
    POSSIBLE {
        public void showState() {
            System.out.println("[System] 반납된 도서입니다.");
        }

        public void showChangeState(){
            System.out.println("[System] 도서가 대여 처리 되었습니다.");
        }
    },
    RENTING {
        public void showChangeState()  {
            System.out.println("[System] 도서가 반납 처리 되었습니다.");
        }
        public void showState(){
            System.out.println("[System] 대여중인 도서입니다.");
        }
    },
    ORGANIZING{
        public void showState() {
            System.out.println("[System] 정리중인 도서입니다.");
        }
        public void showChangeState(){
            System.out.println("[System] 정리중인 도서입니다.");
        }
    },
    LOST{
        public void showState() {
            System.out.println("[System] 분실 처리된 도서입니다.");
        }
        public void showChangeState(){
            System.out.println("[System] 도서가 분실 처리 되었습니다.");
        }
    };

    public abstract void showState();
    public abstract void showChangeState();
}
