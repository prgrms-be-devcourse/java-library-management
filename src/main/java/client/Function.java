package client;

import domain.Book;
import service.Service;

import java.util.Arrays;

public enum Function {
    ADD(1) {
        @Override
        public void excute(Service service, ConsoleManager consoleManager) {
            Book book = consoleManager.addBook();
            service.addBook(book);
            consoleManager.addBookResult();
        }
    },
    GETALL(2){
        @Override
        public void excute(Service service, ConsoleManager consoleManager) {
            consoleManager.getAll(service.getAll());
        }
    },
    SEARCH(3){
        @Override
        public void excute(Service service, ConsoleManager consoleManager) {
            String keyword = consoleManager.searchName();
            consoleManager.searchNamePrint(service.searchName(keyword));
        }
    },
    RENTAL(4){
        @Override
        public void excute(Service service, ConsoleManager consoleManager) {
            int i = consoleManager.rentalBook();
            service.rentalBook(i);
        }
    },
    RETURN(5){
        @Override
        public void excute(Service service, ConsoleManager consoleManager) {
            int i = consoleManager.returnBook();
            service.organizeBook(i);
        }
    },
    LOST(6){
        @Override
        public void excute(Service service, ConsoleManager consoleManager) {
            int i = consoleManager.lostBook();
            service.lostBook(i);
        }
    },
    DELETE(7){
        @Override
        public void excute(Service service, ConsoleManager consoleManager) {
            int i = consoleManager.deleteBook();
            service.deleteBook(i);
        }
    };

    private final int functionNumber;

    Function(int functionNumber){
        this.functionNumber = functionNumber;
    }

    public static Function of(int userSelect){
        return Arrays.stream(values())
                .filter(functionNumber -> functionNumber.isEquals(userSelect))
                .findFirst()
                .orElseThrow();
    }

    private boolean isEquals(int userSelect){
        return this.functionNumber==userSelect;
    }

    public abstract void excute(Service service, ConsoleManager consoleManager);
}
