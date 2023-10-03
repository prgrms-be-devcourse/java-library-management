import config.Appconfig;
import controller.BookController;
import lombok.SneakyThrows;
import repository.Repository;
import view.View;

public class ProgramRunner implements Runnable{
    private final Appconfig appconfig = new Appconfig();
    private Repository repository;
    private View view;
    private BookController bookController;

    @SneakyThrows
    @Override
    public void run() {
        // Dependency 설정
        this.repository = appconfig.getRepository();
        this.view = appconfig.getView();
        bookController = new BookController(view);

        bookController.init();
    }
}
