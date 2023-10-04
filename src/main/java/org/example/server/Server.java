package org.example.server;

import org.example.packet.requestPacket.RequestPacket;
import org.example.packet.responsePacket.ResponsePacket;
import org.example.server.controller.BookController;
import org.example.server.controller.Controller;
import org.example.server.repository.FileRepository;
import org.example.server.repository.InMemoryRepository;
import org.example.server.repository.Repository;
import org.example.server.service.BookService;
import org.example.server.service.Service;

import java.util.function.Supplier;

public class Server {
    private Controller controller;

    public void setMode(String mode) {
        Repository repository = ModeType.valueOf(mode).getRepository();
        Service service = new BookService(repository);
        controller = new BookController(service);
    }

    public ResponsePacket requestMethod(RequestPacket requestPacket) {
        return controller.handleRequest(requestPacket);
    }

    private enum ModeType {
        COMMON(FileRepository::new),
        TEST(InMemoryRepository::new);

        private final Supplier<Repository> RepositorySupplier;

        ModeType(Supplier<Repository> RepositorySupplier) {
            this.RepositorySupplier = RepositorySupplier;
        }

        public Repository getRepository() {
            return RepositorySupplier.get();
        }
    }
}
