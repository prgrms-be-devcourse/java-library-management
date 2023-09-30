import controller.BookController;

public class ManageLibraryApplication {
    public static void main(String[] args){
        BookController bookController = new BookController();
        bookController.selectMode();
    }
}
