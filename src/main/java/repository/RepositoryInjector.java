package repository;

public class RepositoryInjector {

    public static Repository getRepository(int mode) {
        if (mode == 1) {    // 일반 모드
            return new FileRepository();
        }
        return null;
    }
}