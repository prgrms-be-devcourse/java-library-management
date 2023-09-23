package library.book.application;

import library.book.application.dto.request.RegisterBookRequest;

public interface BookManageService extends LibraryService {

	void registerBook(RegisterBookRequest request);
}
