import config.Appconfig;
import controller.BookController;
import input.Input;
import output.Output;
import repository.Repository;

public class ProgramRunner implements Runnable{
    private final Appconfig appconfig = new Appconfig();
    private Input input;
    private Output output;
    private Repository repository;

    @Override
    public void run() {
        // Dependency 설정
        this.input = appconfig.getInput();
        this.output = appconfig.getOutput();
        this.repository = appconfig.getRepository();

        // 모드 설정
        output.selectMode();
    }
}
