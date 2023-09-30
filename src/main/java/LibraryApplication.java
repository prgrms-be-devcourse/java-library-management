import controller.BookController;

public class LibraryApplication {
    public static void main(String[] args){
        BookController bookController = new BookController();
        bookController.selectMode();
    }
}
