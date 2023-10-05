package manage.file;

import domain.Book;
import domain.BookState;
import exception.BookNumberAlreadyExistException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.IntStream;

public class CsvBookFileManager implements BookFileManager {
    private static final String CSV_PATTERN = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    private final String filePath;

    public CsvBookFileManager(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public HashMap<Integer, Book> read() {
        HashMap<Integer, Book> books = new HashMap<>();
        HashSet<Integer> bookNumSet = new HashSet<>();

        File file = new File(this.filePath);

        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("파일 확인 중 문제 발생", e);
        }

        try (BufferedReader br =
                     new BufferedReader(
                             new InputStreamReader(
                                     new FileInputStream(this.filePath), StandardCharsets.UTF_8))){
            String line = br.readLine(); // 첫 행 건너뛰기
            while ((line = br.readLine()) != null) {
                String[] split = line.split(CSV_PATTERN);
                for (int i = 0; i < split.length; i++) split[i] = split[i].replaceAll("\"", "");

                int bookNum = Integer.parseInt(split[0]);
                if (bookNumSet.contains(bookNum))
                    throw new BookNumberAlreadyExistException("파일에 도서번호가 중복됩니다. 다시 확인해 주세요.");

                bookNumSet.add(bookNum);
                books.put(bookNum,
                        new Book(bookNum, split[1], split[2], Integer.parseInt(split[3]), BookState.valueOf(split[4]), Long.parseLong(split[5]))
                );
            }
        } catch (IOException e){
            throw new RuntimeException("파일 읽기 중 문제 발생");
        }

        return books;
    }

    @Override
    public void write(HashMap<Integer, Book> books) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(this.filePath), StandardCharsets.UTF_8))){
            bw.write("도서번호,도서 제목,작가,페이지 수,상태,마지막으로 반납한 시간");
            bw.newLine();
            for(Book book : books.values())
                writeBook(bw, book);

        }catch (FileNotFoundException e){
            throw new RuntimeException("파일이 존재하지 않음");
        }
        catch (IOException e){
            throw new RuntimeException("파일 쓰기 중 문제 발생");
        }
    }

    private void writeBook(BufferedWriter bw, Book book) throws IOException {
        String[] fieldsVal = {String.valueOf(book.getNumber()),
                book.getTitle(),
                book.getAuthor(),
                String.valueOf(book.getPageNum()),
                String.valueOf(book.getBookState()),
                String.valueOf(book.getLastReturn())
        };
        IntStream.rangeClosed(1, 2).forEach(i -> {
            if(fieldsVal[i].contains(",")) fieldsVal[i] = "\"" + fieldsVal[i] + "\"";
        });

        for (int i = 0; i < fieldsVal.length; i++){
            bw.write(fieldsVal[i]);
            if (i != fieldsVal.length - 1) bw.write(',');
        }
        bw.newLine();
    }
}
