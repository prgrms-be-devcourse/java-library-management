package repository;

import domain.Book;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class TestRepository implements Repository{

    private final static int condition_default = 0; // 기본 상태
    private final static int condition_possible = 1; //대여 가능
    private final static int condition_onRent = 2; //대여 중
    private final static int condition_organizing = 3; //정리 중
    private final static int condition_lost = 4; //분실됨

    @Override
    public List<Book> load(List<Book> list) {
        list = new ArrayList<>();
        return list;
    }

    @Override
    public void save(int id, String title, String author, int page, List<Book> list) {
        list.add(new Book(id, title, author, page, "대여 가능")); // 처음에는 대여 가능하게
    }

    @Override
    public List<Book> findByTitle(String searchTitle, List<Book> list) {
        List<Book> foundBooks = new ArrayList<>();

        Iterator<Book> iterator = list.iterator();

        while (iterator.hasNext()) {
            Book book = iterator.next();
            String title = book.getTitle();
            // title에서 검색어가 포함되어 있는지 확인 (대소문자 무시)
            if (title.contains(searchTitle)) {
                int id = book.getId();
                String author = book.getAuthor();
                int page = book.getPage();
                String condition = book.getCondition();

                foundBooks.add(new Book(id, title, author, page, condition));
            }
        }
        return foundBooks;
    }

    @Override
    public int rentById(int rentId, List<Book> list) {
        int condition = condition_default;
        int flag = 1;

        Iterator<Book> iterator = list.iterator();

        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == rentId) {
                if (Objects.equals(book.getCondition(), "대여 가능")) {
                    flag = 2; // 대여 가능한 상황
                    book.setCondition("대여 중");
                } else if (book.getCondition().equals("분실됨") || book.getCondition().equals("도서 정리중")) {
                    flag = 3; // 분실이거나 정리중인 상황이거나 책이 없는 경우
                }
                break; // ID를 찾았으므로 루프 종료
            }
        }
        return flag;
    }

    @Override
    public int  returnById(int returnId, List<Book> list) {
        int flag = 1; // -> 대여 중인 상황

        for (Book book : list) {
            if (book.getId() == returnId) {
                if(Objects.equals(book.getCondition(), "대여 중")){
                    flag = 2; // 대여 가능한 상황으로 (반납 가능)
                    book.setCondition("대여 가능");
                }
                else if (book.getCondition().equals("분실됨") || book.getCondition().equals("도서 정리중")) {
                    flag = 3; // 반납 불가능
                }
                break; // ID를 찾았으므로 루프 종료
            }
        }

        return flag;
    }

    @Override
    public String lostById(int lostId, List<Book> list) {
        String condition = "";

        for (Book book : list) {
            if (book.getId() == lostId) {
                if(Objects.equals(book.getCondition(), "분실됨")){ //이미 분실된 것
                    condition = "[System] 이미 분실 처리된 도서입니다.";
                }
                else { //
                    book.setCondition("분실됨");
                    condition = "[System] 도서가 분실 처리 되었습니다.";
                }
                break; // ID를 찾았으므로 루프 종료
            }
        }

        return condition;
    }

    @Override
    public String deleteById(int deleteId, List<Book> list) {
        //condition 1은 존재하는 상태에서 지움
        //condition 2는 이미 없는 책

        String message = "";
        boolean flag = false;

        Iterator<Book> iterator = list.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == deleteId) {
                iterator.remove(); // delete Id를 가진 레코드를 삭제
                flag = true;
                message = "도서가 삭제 처리 되었습니다.";
            }
        }

        if(!flag) message = "존재하지 않는 도서번호 입니다.";

        return message;
    }
}
