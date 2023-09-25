package util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CsvFileUtil {

    public static Long findLastId(String csvFile) {
        File file = new File(csvFile);
        if (!file.exists()) {
            return 0L;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String lastColumnValue = null;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // CSV 열 구분 기호에 따라 변경

                if (columns.length > 0) {
                    lastColumnValue = columns[0]; // 첫 번째 열 값을 저장
                }
            }

            if (lastColumnValue != null) {
                return Long.parseLong(lastColumnValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }
}
