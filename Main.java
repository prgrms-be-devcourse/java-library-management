import manage.FileManager;
import manage.FileManagerImpl;
import manage.ListBookManager;
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

        TotalManager totalManager;
        if(mode == 1) {
            FileManager fileManager = new FileManagerImpl();
            totalManager = new TotalManager(new ListBookManager(fileManager.readCsv("res/temp.csv")), sc);
        }else{
            totalManager = new TotalManager(new ListBookManager(), sc);
        }

        totalManager.run();
    }
}
