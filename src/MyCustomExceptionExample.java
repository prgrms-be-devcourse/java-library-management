import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MyCustomException extends RuntimeException {

    public MyCustomException(String message) {
        super(message);
    }
}

public class MyCustomExceptionExample {

    public static void myFunction() {
        throw new MyCustomException("Custom Exception Message");
    }

    public static void main(String[] args) {
//        try {
//            myFunction();
//        } catch (MyCustomException e) {
//            assertThatThrownBy(() -> {throw e;})
//                    .isInstanceOf(MyCustomException.class)
//                    .hasMessageContaining("Custom Exception Message");
//        }
        assertThatThrownBy(() -> myFunction())
                .isInstanceOf(MyCustomException.class)
                .hasMessageContaining("Custom Exception Message");
    }
}
