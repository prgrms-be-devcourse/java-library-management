package org.example;

public enum BookState {
    POSSIBLE{
        public void showPossibleState() {
            System.out.println("[System] 도서가 대여 처리 되었습니다.");
        }

        public void showNotPossibleState(){
            System.out.println("[System] 이미 대여중인 도서입니다.");
        }
    },
    NOT_POSSIBLE{
        public void showNotPossibleState()  {
            System.out.println("[System] 원래 대여가 가능한 도서입니다.");
        }
        public void showPossibleState(){
            System.out.println("[System] 도서가 반납 처리 되었습니다.");
        }
    },
    ORGANIZING{
        public void showPossibleState() {
            System.out.println("[System] 정리중인 도서입니다.");
        }
        public void showNotPossibleState(){
            System.out.println("[System] 정리중인 도서입니다.");
        }
    },
    LOST{
        public void showPossibleState() {
            System.out.println("[System] 도서가 분실 처리 되었습니다.");
        }
        public void showNotPossibleState(){
            System.out.println("[System] 이미 분실 처리된 도서입니다.");
        }
    };

    public abstract void showPossibleState();
    public abstract void showNotPossibleState();
}
