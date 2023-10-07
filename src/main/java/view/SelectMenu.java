package view;

import message.ExecuteMessage;
import domain.Book;
import service.Service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static domain.ConsolePrint.*;

public enum SelectMenu {
    REGISTER(1) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.REGISTER.getMessage());
            Book book = getBook();
            service.register(book);
            System.out.println(ExecuteMessage.COMPLETE_REGISTER.getMessage());
            return true;
        }
    },LIST(2) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.LIST.getMessage());
            printListView(service.getList());
            System.out.println(ExecuteMessage.LIST_FINISH.getMessage());
            return true;
        }
    }, SEARCH(3) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.SEARCH.getMessage());
            String title = getTitle();
            searchView(service.search(title));
            System.out.println(ExecuteMessage.SEARCH_FINISH.getMessage());
            return true;
        }
    }, RENTAL(4) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.RENTAL.getMessage());
            int id = getRentalId();
            resultView(service.rental(id));
            return true;
        }
    }, RETURN(5) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.RETURN.getMessage());
            int id = getReturnId();
            resultView(service.returnBook(id));
            return true;
        }
    }, LOST(6) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.LOST.getMessage());
            int id = getLostId();
            resultView(service.lostBook(id));
            return true;
        }
    }, DELETE(7) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.DELETE.getMessage());
            int id = getDeleteId();
            resultView(service.deleteBook(id));
            return true;
        }
    };

    private static final Map<Integer, SelectMenu> BY_STRING =
            Stream.of(values())
                    .collect(Collectors.toMap(SelectMenu :: getSelectNum, menu -> menu));
    private final int selectNum;

    SelectMenu(int selectNum) {
        this.selectNum = selectNum;
    }

    public boolean run(Service service) {
        return false;
    }

    public int getSelectNum() {
        return this.selectNum;
    }

    public static SelectMenu valueOfSelectNum(int selectNum) {
        return BY_STRING.get(selectNum);
    }
}
