package library.book.application;

import library.book.application.dto.request.RegisterBookRequest;

public interface BookManageService extends BookService {

	void registerBook(RegisterBookRequest request);
}
