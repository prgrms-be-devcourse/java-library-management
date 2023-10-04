package org.example.server;

import org.example.packet.Request;
import org.example.server.controller.BookController;
import org.example.server.controller.Controller;
import org.example.server.exception.ServerException;
import org.example.server.repository.FileRepository;
import org.example.server.repository.InMemoryRepository;
import org.example.server.repository.Repository;
import org.example.server.service.BookService;
import org.example.server.service.Service;

import java.util.function.Supplier;

public class Server {
    private Controller controller;
    private Repository repository;
    private int saveTimer;

    public void setServer(String mode) {
        repository = ModeType.valueOf(mode).getRepository();
        Service service = new BookService(repository);
        controller = new BookController(service);
        saveTimer = 0;
    }

    public String requestMethod(Request request) {
        try {
            if (++saveTimer == 5) {
                saveTimer = 0;
                saveData();
            }
            return controller.mapController(request);
        } catch (ServerException e) {
            return e.getMessage();
        }
    }

    public void saveData() {
        if (repository instanceof FileRepository) {
            repository.save();
        }
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
