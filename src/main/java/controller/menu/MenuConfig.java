package controller.menu;

import controller.BookController;
import manager.console.InputManager;
import manager.console.OutputManager;
import service.BookService;

public class MenuConfig {

    private final OutputManager outputManager;
    private final InputManager inputManager;
    private final BookService bookService;

    public MenuConfig(OutputManager outputManager, InputManager inputManager, BookService bookService) {
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.bookService = bookService;
    }

    public void selectMenu() {
        String function = "";
        while (!function.equals("8")) {
            outputManager.printSelectedMenu();
            try {
                function = inputManager.getStringInput();
                if (function.equals("8")) break;
                MenuType menuType = MenuType.findMenuTypeByMenu(function);
                BookController bookController = new BookController(menuType, bookService);
                menuType.executeController(bookController);
            } catch (NumberFormatException e) {
                outputManager.printSystem("숫자를 입력해주세요.");
            } catch (Exception e) {
                outputManager.printSystem(e.getMessage());
            }
        }
    }
}
