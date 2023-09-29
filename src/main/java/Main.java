import app.library.management.App;
import app.library.management.infra.console.Console;
import app.library.management.infra.mode.ExecutionMode;

public class Main {

    public static void main(String[] args) {

        Console console = new Console();
        console.selectMode();
        ExecutionMode mode = ExecutionMode.fromNum(console.inputInt());

        App app = new App(mode);
        app.logic();
    }
}
