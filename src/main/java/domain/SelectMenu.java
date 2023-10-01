package domain;

import message.ExecuteMessage;
import service.Service;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SelectMenu {
    REGISTER(1) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.REGISTER.getMessage());
            service.register();
            return true;
        }
    },LIST(2) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.LIST.getMessage());
            service.list();
            return true;
        }
    }, SEARCH(3) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.SEARCH.getMessage());
            service.search();
            return true;
        }
    }, RENTAL(4) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.RENTAL.getMessage());
            service.rental();
            return true;
        }
    }, RETURN(5) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.RETURN.getMessage());
            service.returnBook();
            return true;
        }
    }, LOST(6) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.LOST.getMessage());
            service.lostBook();
            return true;
        }
    }, DELETE(7) {
        @Override
        public boolean run(Service service) {
            System.out.println(ExecuteMessage.DELETE.getMessage());
            service.deleteBook();
            return true;
        }
    };

    private static Map<Integer, SelectMenu> BY_STRING =
            Stream.of(values())
                    .collect(Collectors.toMap(SelectMenu :: getSelectNum, e -> e));
    private int selectNum;

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
