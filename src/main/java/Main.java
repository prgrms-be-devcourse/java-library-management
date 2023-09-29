import library.management.config.Configuration;
import library.management.controller.BookController;
import library.management.infra.console.Console;

public class App {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        BookController bookController = configuration.bookController();
        Console console = configuration.console();

        while(true) {
            console.selectMenu();
            int num = console.inputInt();

            if (num == 8) break;

            switch(num) {
                case 1 : bookController.saveBook(); break;
                case 2 : bookController.findAll(); break;
                case 3 : bookController.findByTitle(); break;
                case 4 : bookController.rentById(); break;
                case 5 : bookController.returnById(); break;
                case 6 : bookController.reportLostById(); break;
                case 7 : bookController.deleteById(); break;
                default :
                    System.out.println("유효하지 않은 값입니다.");
                    break;
            }

        }
    }
}
