package java_library_management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class Client {

    public static void main(String[] args) throws IOException, NoSuchMethodException {

        System.out.println("0. 모드를 선택해주세요.");
        System.out.println("1. 일반 모드");
        System.out.println("2. 테스트 모드");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        LibraryManagement library = new LibraryManagement();
        Mode mode;

        while (true) {

            int mode_id = Integer.parseInt(bufferedReader.readLine());

            if (mode_id == 1) {
                mode = new General();
                library.setMode(mode);
                library.setCallback(mode::load, mode::update);
                break;
            } else if (mode_id == 2) {
                mode = new Tests();
                library.setMode(mode);
                library.setCallback(null, null);
                break;
            } else {
                System.out.println("[System] 존재하지 않는 모드입니다.\n");
            }
        }

        String filePath = "src/java_library_management/Book.json";
        Map<Integer, Book> map = new TreeMap<>();
        library.printStartMsg();
        BiConsumer<Map<Integer, Book>, String> loadCallback = library.getLoadCallback();
        BiConsumer<Map<Integer, Book>, String> updateCallback = library.getUpdateCallback();
        library.play(map, filePath, loadCallback, updateCallback);
    }
}
