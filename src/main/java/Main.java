import exception.BookNumberAlreadyExistException;
import manage.file.CsvFileManager;
import manage.book.ListBookManager;
import manage.file.TestFileManager;
import manage.TotalManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int mode;
        Scanner sc = new Scanner(System.in);
        System.out.print("모드를 선택합니다. 1. 운용 모드 2. 테스트 모드\n> ");
        while (true) {
            try {
                mode = Integer.parseInt(sc.nextLine().trim());
                if(mode < 1 || mode > 2) throw new NumberFormatException();
                break;
            }catch (NumberFormatException e){
                System.out.println("잘못된 입력입니다.");
            }
        }

        try {
            TotalManager totalManager;
            if (mode == 1)
                totalManager = new TotalManager(new ListBookManager(new CsvFileManager("temp.csv")), sc);
            else
                totalManager = new TotalManager(new ListBookManager(new TestFileManager()), sc);

            totalManager.run();
        }catch (BookNumberAlreadyExistException e){
            System.out.println("읽어오는 파일에 중복되는 도서번호가 존재하여 불러올 수 없습니다.");
        }
    }
}
