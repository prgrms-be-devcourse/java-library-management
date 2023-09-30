package manager;

import domain.Book;
import domain.Status;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final String PATH = System.getProperty("user.dir")+ "/src/main/resources/book_data.csv";
    // file -> list
    public List<Book> loadData() {
        List<Book> bookList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(PATH))){
            String line="";
            while ((line = br.readLine())!=null){
                String[] split = line.split(",");
                Integer id = Integer.valueOf(split[0]);
                String title = split[1];
                String author = split[2];
                Integer page = Integer.valueOf(split[3]);
                Status status = Status.valueOf(split[4]);
                Instant returnTime = null;
                if (!split[5].equals("null"))
                    returnTime = Instant.parse(split[5]);
                Book book = new Book(id,title,author,page, status,returnTime);
                bookList.add(book);
            }
        } catch(IOException e) {
            throw new RuntimeException();
        }
        return bookList;
    }

    // list -> file
    public void updateFile(List<Book> bookList){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))){
            for (Book book : bookList){
                bw.write(book.getId()+","+book.getTitle()+","+book.getAuthor()+","+book.getPage()+","+book.getStatus()+","+book.getReturnTime());
                bw.newLine();
            }
        } catch(IOException e){
            throw new RuntimeException();
        }
    }
}