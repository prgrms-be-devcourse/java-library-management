package library.book.manager;

import java.util.function.Supplier;

import library.book.application.BookService;
import library.book.domain.BookRepository;
import library.book.infra.repository.IoBookRepository;
import library.book.infra.repository.TestBookRepository;

public enum ModeManager {

	ONE(
		() -> new IoBookRepository("src/main/resources/static/books.json")
	),
	TWO(
		TestBookRepository::new
	);

	private final Supplier<BookRepository> repositorySupplier;

	ModeManager(final Supplier<BookRepository> repositorySupplier) {
		this.repositorySupplier = repositorySupplier;
	}

	public BookRepository getRepository() {
		return this.repositorySupplier.get();
	}
}
