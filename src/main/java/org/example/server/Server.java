package org.example.server;

import org.example.packet.Request;
import org.example.server.controller.BookController;
import org.example.server.controller.Controller;
import org.example.server.repository.FileBookRepository;
import org.example.server.repository.Repository;
import org.example.server.repository.TestBookRepository;
import org.example.server.service.BookService;
import org.example.server.service.Service;

import java.util.function.Supplier;

// 서버는 싱글톤으로 생성
// 기능 1: 클라이언트로부터 받은 모드에 따라 controller, service, repository 세팅
// 기능 2: 클라이언트로부터 받은 메뉴(요청) 수행하고 String으로 응답
public class Server {
    private enum ServerMode { // 모드가 늘어날 경우를 대비하기 위해, enum으로 레포지토리 생성 기능 매핑.
        COMMON(FileBookRepository::new),
        TEST(TestBookRepository::new);

        private final Supplier<Repository> RepositorySupplier;

        ServerMode(Supplier<Repository> RepositorySupplier) {
            this.RepositorySupplier = RepositorySupplier;
        }

        public Repository getRepository() {
            return RepositorySupplier.get();
        }
    }

    private Server() {
    }

    private static Controller controller;
    private static Repository repository;

    public static void setServer(String mode) {
        repository = ServerMode.valueOf(mode).getRepository();
        Service service = new BookService(repository);
        controller = new BookController(service);
    } // 모드에 따라 서버 레이어 세팅. 외부(Server)에서 레이어 클래스 의존성을 주입하고자 했습니다.

    public static String requestMethod(Request request) {
        try {
            return controller.mapController(request); // 요청 성공 응답
        } catch (RuntimeException e) {
            return e.getMessage(); // 요청 실패 응답
        }
    } // 클라이언트로부터 받은 요청 수행 후 String으로 응답

    public static void saveData() {
        if (repository instanceof FileBookRepository) {
            repository.save();
        } // 프로그램 에러 발생해서 종료시 데이터 저장(일반 모드)
    }
}
