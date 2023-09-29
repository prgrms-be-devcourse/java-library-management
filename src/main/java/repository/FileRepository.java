package repository;

import domain.Book;
import domain.Status;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileRepository implements Repository{

    private final String path = System.getProperty("user.dir") + "/src/main/resources/repository.csv";
    private final List<Book> bookList = new ArrayList<>();

    public FileRepository(){
        File repository = new File(path);
        loadFile();
    }

    private void loadFile(){
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String data;
            while((data=br.readLine())!=null){
                String[] splitData = data.split(",");
                Book book = new Book(Long.parseLong(splitData[0]), splitData[1], splitData[2], Integer.parseInt(splitData[3]), Status.valueOf(splitData[4]));
                bookList.add(book);
            }
        }catch(IOException e){
            System.out.println();
        }
    }

    private void writeFile(){
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), StandardCharsets.UTF_8));
            for (Book book : bookList) {
                bw.write(book.getId() + "," + book.getName() + "," + book.getAuthor() + "," + book.getPage() + "," + book.getStatus().getStatusName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addBook(Book book) {
        int size = bookList.size();
        Long id = size > 0 ? bookList.get(size - 1).getId() + 1 : 0;
        book.setId(id);
        bookList.add(book);
        writeFile();
    }

    @Override
    public List<Book> getAll() {
        return bookList;
    }

    @Override
    public List<Book> searchBook(String name) {
        List<Book> searchResult = new ArrayList<>();
        bookList.stream()
                .filter(book->book.getName().contains(name))
                .forEach(searchResult::add);
        return searchResult;
    }

    @Override
    public Book getBook(Long bookNumber) {
        return bookList.stream()
                .filter(b -> b.getId().equals(bookNumber))
                .findAny()
                .orElseThrow(() -> new RuntimeException("[System] 존재하지 않는 도서번호 입니다."));
    }

    @Override
    public void deleteBook(Long bookNumber) {
        Book book = getBook(bookNumber);
        bookList.remove(book);
        writeFile();
    }
}
