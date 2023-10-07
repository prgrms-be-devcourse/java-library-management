package repository;

import domain.Book;
import domain.Status;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileRepository implements Repository{

    private final String path = System.getProperty("user.dir") + "/repository.csv";
    private final String seperator = ",";
    private final List<Book> bookList = new ArrayList<>();

    public FileRepository(){
        loadFile();
    }

    private void loadFile(){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))){
            String data;
            while((data=br.readLine())!=null){
                String[] splitData = data.split(",");
                Book book = new Book(Long.parseLong(splitData[0]), splitData[1], splitData[2], Integer.parseInt(splitData[3]), Status.of(splitData[4]));
                bookList.add(book);
            }
        }catch(IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private void writeFile(){
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, false), StandardCharsets.UTF_8))){
            for (Book book : bookList) {
                bw.write(book.infoForFile(seperator));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
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
        return new ArrayList<>(bookList);
    }

    @Override
    public List<Book> searchBook(String name) {
        return bookList.stream()
                .filter(book -> book.containsName(name))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> getBook(Long bookNumber) {
        return bookList.stream()
                .filter(book -> book.equalsId(bookNumber))
                .findFirst();
    }

    @Override
    public void deleteBook(Book book) {
        bookList.remove(book);
        writeFile();
    }
}
