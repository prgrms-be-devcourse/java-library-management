package org.example.server;

import org.example.packet.Request;
import org.example.server.repository.BookRepository;
import org.example.server.repository.FileBookRepository;
import org.example.server.repository.TestBookRepository;

import java.util.function.Supplier;

// 싱글톤으로 생성
// 콘솔에서 입력 받은 모드, 메뉴, 각 메뉴에 대한 입력 값들을 세팅.
public class Server {
    private enum ServerMode {
        COMMON(FileBookRepository::new),
        TEST(TestBookRepository::new);

        private final Supplier<BookRepository> bookRepositorySupplier;

        ServerMode(Supplier<BookRepository> bookRepositorySupplier) {
            this.bookRepositorySupplier = bookRepositorySupplier;
        }

        public BookRepository getRepository() {
            return bookRepositorySupplier.get();
        }
    }

    private Server() {
    }

    public static void requestMode(String mode) {
        BookService.repository = ServerMode.valueOf(mode).getRepository();
    } // 레포지토리 모드에 따라 세팅


    public static String requestMethod(Request request) {
        return BookController.valueOf(request.method).mapping(request.requestData); // 예외 처리?
    } // 메뉴에 다른 메서드 매핑 및 호출
}
