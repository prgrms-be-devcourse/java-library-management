package service;

public interface Service {

    //일반 모드는 csv에서 데이터를 가져오는 코드
    //테스트 모드는 list 초기화
    public void load();

    //main화면을 출력하는 코드
    public void mainView();

    //도서 등록 - 1
    public void registration();

    //전체 도서 목록을 조회하는 코드 - 2
    public void viewAll();

    //도서 검색 by title - 3
    public void findByTitle();

    //도서 대여 by id - 4
    public void rentBook();

    //도서 반납 by id - 5
    public void returnBook();

    //도서 분실 처리 by id - 6
    public void lostBook();

    //도서 삭제 by id - 7
    public void deleteBook();

    //종료하는 코드 - 8
    public void exit();

}
