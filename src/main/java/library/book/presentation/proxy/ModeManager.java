package library.book.presentation.proxy;

import java.util.function.Function;
import java.util.function.Supplier;

import library.book.application.BookService;
import library.book.application.DefaultBookService;
import library.book.application.proxy.DataSaver;
import library.book.domain.BookRepository;
import library.book.infra.repository.IoBookRepository;
import library.book.infra.repository.TestBookRepository;

public enum ModeManager {

	ONE(
		() -> new IoBookRepository("src/main/resources/static/books.json"),
		bookRepository -> new DataSaver(new DefaultBookService(bookRepository), bookRepository)
	),
	TWO(
		TestBookRepository::new,
		DefaultBookService::new
	);

	private final Supplier<BookRepository> repositorySupplier;
	private final Function<BookRepository, BookService> serviceFunction;

	ModeManager(
		final Supplier<BookRepository> repositorySupplier,
		final Function<BookRepository, BookService> serviceFunction
	) {
		this.repositorySupplier = repositorySupplier;
		this.serviceFunction = serviceFunction;
	}

	public BookRepository getRepository() {
		return this.repositorySupplier.get();
	}

	public BookService getService(final BookRepository bookRepository) {
		return this.serviceFunction.apply(bookRepository);
	}
}
