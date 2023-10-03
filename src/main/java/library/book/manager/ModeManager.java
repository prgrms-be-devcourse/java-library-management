package library.book.manager;

import java.util.function.Supplier;

import library.book.domain.BookRepository;
import library.book.infra.repository.IoBookRepository;
import library.book.infra.repository.InMemoryBookRepository;

public enum ModeManager {

	ONE(
		() -> new IoBookRepository("src/main/resources/static/books.json")
	),
	TWO(
		InMemoryBookRepository::new
	);

	private final Supplier<BookRepository> repositorySupplier;

	ModeManager(final Supplier<BookRepository> repositorySupplier) {
		this.repositorySupplier = repositorySupplier;
	}

	public BookRepository getRepository() {
		return this.repositorySupplier.get();
	}
}
