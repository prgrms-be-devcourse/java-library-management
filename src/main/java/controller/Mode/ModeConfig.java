package controller.Mode;

import controller.BookController;
import manager.console.InputManager;
import manager.console.OutputManager;
import service.BookService;

public class ModeConfig {
    private final OutputManager outputManager = new OutputManager();
    private final InputManager inputManager = new InputManager();

    public void selectMode() {
        outputManager.printSelectMode();
        String mode;
        try {
            mode = inputManager.getStringInput();
        } catch (Exception e) {
            outputManager.printSystem(e.getMessage());
            return;
        }
        applyMode(mode);
    }

    public void applyMode(String mode) {
        ModeType modeType = ModeType.findModeTypeByMode(mode);
        if (modeType == null) {
            outputManager.printSystem("잘못된 입력입니다.");
            return;
        }
        BookService bookService = ModeType.findService(modeType, outputManager);
        ModeType.printModeExecution(modeType, outputManager);

        BookController bookController = new BookController(bookService, inputManager, outputManager);
        bookController.selectMenu();
    }
}
