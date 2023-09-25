package library.book.presentation.proxy;

import java.util.function.Supplier;

import library.book.domain.BookRepository;
import library.book.infra.repository.IoBookRepository;
import library.book.infra.repository.TestBookRepository;

public enum ModeManager {

	ONE(() -> new IoBookRepository("src/main/resources/static/books.json")),
	TWO(TestBookRepository::new)
	;

	private final Supplier<BookRepository> repositorySupplier;

	ModeManager(Supplier<BookRepository> repositorySupplier) {
		this.repositorySupplier = repositorySupplier;
	}

	public BookRepository getRepository() {
		return this.repositorySupplier.get();
	}
}
