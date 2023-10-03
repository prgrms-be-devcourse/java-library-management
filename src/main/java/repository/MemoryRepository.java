package repository;

import domain.Book;
import domain.BookStatusType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryRepository implements Repository{
    private static final List<Book> bookList = new ArrayList<>();
    // 다음부턴 Map으로 하는게 좋겠다
    private final String FILE_PATH = "src/main/resources/도서목록.csv";
    private File file;
    private static Long bookNoSeq = 1L;

    public MemoryRepository() {
        file = new File(FILE_PATH);
        if(!file.exists()) createFile();
        loadBooks();
    }

    /**
     * 파일 생성
     */
    private void createFile(){
        try{
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("파일을 생성할 수 없습니다!");
        }
    }

    /**
     * 파일 로딩
     */
    private void loadBooks(){
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(":");

                Book tempBook = Book.builder().id(Long.valueOf(data[0]))
                        .title(data[1])
                        .author(data[2])
                        .pageNum(Integer.valueOf(data[3]))
                        .bookStatusType(Enum.valueOf(BookStatusType.class, data[4])).build();
                bookList().add(tempBook);
            }
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    /**
     * 파일 저장
     */
    private void saveBooks(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Book book : bookList) {
                bw.write(book.getId() + ":" +
                        book.getTitle() + ":" +
                        book.getAuthor() + ":" +
                        book.getPageNum() + ":" +
                        book.getBookStatusType() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("데이터를 저장할 수 없습니다!");
        }
    }

    private void addBookNoSeq(){
        bookNoSeq++;
    }

    @Override
    public void addBook(Book book) {
        book.setId(bookNoSeq);
        addBookNoSeq();
        bookList.add(book);
        saveBooks();
    }

    @Override
    public void deleteBook(Book book) {
        bookList.remove(book);
        saveBooks();
    }

    @Override
    public Optional<Book> findById(Long bookNo) {
        return bookList.stream().filter(book -> book.getId().equals(bookNo)).findAny();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookList.stream().filter(book -> book.getTitle().equals(title)).collect(Collectors.toList());
    }

    @Override
    public List<Book> bookList() {
        return this.bookList;
    }
}
