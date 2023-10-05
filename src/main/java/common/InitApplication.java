package common;

import exception.BookNumberAlreadyExistException;
import manage.TotalManager;
import manage.book.BookListManager;
import manage.file.CsvFileManager;
import manage.file.TestFileManager;

import static common.Util.CONSOLE_INPUT;

public class InitApplication {
    private static final int PROD_MODE = 1;
    private static final int TEST_MODE = 2;
    private static final String FILE_PATH = "./src/main/resources/temp.csv";

    public static void run(){
        int mode;
        System.out.print("모드를 선택합니다. 1. 운용 모드 2. 테스트 모드" + System.lineSeparator() + "> ");

        while (true) {
            try {
                mode = Integer.parseInt(CONSOLE_INPUT.nextLine().strip());
                if(mode != PROD_MODE && mode != TEST_MODE) throw new NumberFormatException();
                break;
            }catch (NumberFormatException e){
                System.out.println("잘못된 입력입니다.");
            }
        }

        try {
            TotalManager totalManager;
            if (mode == PROD_MODE)
                totalManager = new TotalManager(new BookListManager(new CsvFileManager(FILE_PATH)));
            else
                totalManager = new TotalManager(new BookListManager(new TestFileManager()));

            totalManager.run();
        }catch (BookNumberAlreadyExistException e){
            System.out.println("읽어오는 파일에 중복되는 도서번호가 존재하여 불러올 수 없습니다.");
        }
    }
}
