package client;

import domain.Book;
import service.Service;

import java.util.Arrays;
import java.util.NoSuchElementException;

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
            consoleManager.getAllResult(service.getAll());
        }
    },
    SEARCH(3){
        @Override
        public void excute(Service service, ConsoleManager consoleManager) {
            String keyword = consoleManager.searchName();
            consoleManager.searchNameResult(service.searchName(keyword));
        }
    },
    RENTAL(4){
        @Override
        public void excute(Service service, ConsoleManager consoleManager) {
            int i = consoleManager.rentalBook();
            service.rentalBook(i);
            consoleManager.rentalResult();
        }
    },
    RETURN(5){
        @Override
        public void excute(Service service, ConsoleManager consoleManager) {
            final Long sleepTime = 300000L;
            int i = consoleManager.returnBook();
            service.returnBook(i, sleepTime);
            consoleManager.returnResult();
        }
    },
    LOST(6){
        @Override
        public void excute(Service service, ConsoleManager consoleManager) {
            int i = consoleManager.lostBook();
            service.lostBook(i);
            consoleManager.lostResult();
        }
    },
    DELETE(7){
        @Override
        public void excute(Service service, ConsoleManager consoleManager) {
            int i = consoleManager.deleteBook();
            service.deleteBook(i);
            consoleManager.deleteResult();
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
                .orElseThrow(() -> new NoSuchElementException("[System] 목록에 있는 번호를 입력해주세요."));
    }

    private boolean isEquals(int userSelect){
        return this.functionNumber==userSelect;
    }

    public abstract void excute(Service service, ConsoleManager consoleManager);
}
