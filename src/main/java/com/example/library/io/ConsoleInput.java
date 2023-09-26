package com.example.library.io;

import com.example.library.validation.InputCheck;

import java.util.Scanner;

/**
 * //1번. 도서 등록 -> 제목입력, 작가이름입력, 페이지수 입력 . 3개 입력후 다시 메뉴로
 * //2번.->도서목록 전체조회 ->input필요 X
 * //3번 ->도서 제목으로 전체조회 -> 제목(일부)입력 --> 위에 enterTitle()사용하기
 * //4번 ->도서 대여 -> 도서번호(==id) 입력 -> 대여 처리에 대한 결과보여줌(성공,실패)
 * //5번 ->도서 반납 -> 도서번호(==id) 입력 -> 반납 처리에 대한 결과보여줌(성공or실패(원래 대출가능도서))
 * //6번 ->도서 분실처리 ->도서번호(==id)입력 -> 분실 처리에[ 대한 결과 보여줌(성공or실패(원래 분실처리됨))
 * //7번 ->도서 삭제처리 -도서번호(==id)입력 -> 삭제처리에 대한 결과 보여줌(성공or실패(이미없는거_))
 */

public class ConsoleInput {
    Scanner scanner;

    public ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    //모드 선택
    public int selectMode() {
        return InputCheck.isModeNumber(scanner.nextLine());
    }

    public int selectMenu() {
        return InputCheck.isMenuNumber(scanner.nextLine());
    }

    public String enterTitle() {
        return scanner.nextLine();
    }

    public String enterWriter() {
        return scanner.nextLine();
    }

    public String enterPageNumber() {
        return scanner.nextLine();
    }

    public int enterBookId() {
        return InputCheck.isNumber(scanner.nextLine());
    }

}

