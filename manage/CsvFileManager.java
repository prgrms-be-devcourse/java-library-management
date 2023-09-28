package manage;

import entity.Book;
import entity.State;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvFileManager implements FileManager {
    private static final String CSV_PATTERN = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    @Override
    public List<Book> read(String filePath) {
        List<Book> bookList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));

            String line = bufferedReader.readLine(); // 첫 행 건너뛰기
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(CSV_PATTERN);
                for (int i = 0; i < split.length; i++) split[i] = split[i].replaceAll("\"", "");
                bookList.add(
                        new Book(Integer.parseInt(split[0]), split[1], split[2], Integer.parseInt(split[3]), State.valueOf(split[4]), Long.parseLong(split[5]))
                );
            }
        }catch (Exception e){
            throw new RuntimeException("파일 읽기 중 문제 발생");
        }

        return bookList;
    }

    @Override
    public void write(List<Book> bookList) {

    }
}
