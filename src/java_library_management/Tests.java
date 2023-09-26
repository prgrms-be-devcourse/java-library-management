package java_library_management;

public class Tests implements Mode {

    @Override
    public void printStartMsg() {
        System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다.\n");
    }
}
