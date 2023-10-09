package app.library.management.config.factory;

import app.library.management.config.util.PropertiesUtil;
import app.library.management.core.repository.BookRepository;
import app.library.management.core.repository.file.FileStorage;
import app.library.management.core.repository.file.FileStorageAdaptor;
import app.library.management.core.repository.memory.BookMemoryRepository;
import app.library.management.infra.mode.ExecutionMode;

public class BookRepositoryFactory {

	public BookRepository getInstance(ExecutionMode executionMode) {
		if (executionMode.equals(ExecutionMode.GENERAL)) {
			return new FileStorageAdaptor(new FileStorage(PropertiesUtil.getProperty("book.repository.path")));
		}
		return new BookMemoryRepository();
	}
}
