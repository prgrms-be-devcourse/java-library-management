package service;

public interface Service {

    //일반 모드는 csv에서 데이터를 가져오는 코드
    //테스트 모드는 list 초기화
    void load();

    //main화면을 출력하는 코드
    void mainView();

    //도서 등록 - 1
    void registration();

    //전체 도서 목록을 조회하는 코드 - 2
    void viewAll();

    //도서 검색 by title - 3
    void findByTitle();

    //도서 대여 by id - 4
    void rentBook();

    //도서 반납 by id - 5
    void returnBook();

    //도서 분실 처리 by id - 6
    void lostBook();

    //도서 삭제 by id - 7
    void deleteBook();

    //종료하는 코드 - 8
    void exit();

}
