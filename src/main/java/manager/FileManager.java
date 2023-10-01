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
                String[] data = line.split(",");
                Instant returnTime = null;
                if (!data[5].equals("null"))
                    returnTime = Instant.parse(data[5]);

                Book book = Book.builder()
                        .id(Integer.valueOf(data[0]))
                        .title(data[1])
                        .author(data[2])
                        .page(Integer.parseInt(data[3]))
                        .status(Status.valueOf(data[4]))
                        .returnTime(returnTime)
                        .build();
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