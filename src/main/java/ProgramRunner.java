import config.Appconfig;
import controller.BookController;
import repository.Repository;
import service.BookService;
import view.ConsoleView;
import view.View;
import vo.NumberVo;

public class ProgramRunner implements Runnable{
    private final Appconfig appconfig = new Appconfig();
    private Repository repository;
    private View view;
    private BookController bookController;

    @Override
    public void run() {
        // Dependency 설정
        this.repository = appconfig.getRepository();
        this.view = appconfig.getView();
        bookController = new BookController(view);

        bookController.init();
    }
}
