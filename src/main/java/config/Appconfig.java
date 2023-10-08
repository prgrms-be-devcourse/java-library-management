package config;

import controller.BookController;
import repository.MemoryRepository;
import repository.Repository;
import view.ConsoleView;
import view.View;

public class Appconfig {

    private final Repository repository = new MemoryRepository();
    private final View view = new ConsoleView();

    public View getView(){
        return view;
    }
    public Repository getRepository(){
        return repository;
    }
}
