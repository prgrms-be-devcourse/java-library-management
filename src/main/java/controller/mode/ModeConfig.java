package controller.mode;

import controller.menu.MenuConfig;
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
        BookService bookService = modeType.getBookService();
        modeType.printModeExecution();


        MenuConfig menuConfig = new MenuConfig(outputManager, inputManager, bookService);
        menuConfig.selectMenu();
    }
}
