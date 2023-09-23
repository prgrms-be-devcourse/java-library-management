package library.book.application;

import library.book.application.dto.request.RegisterBookRequest;

public interface BookService {

	void registerBook(RegisterBookRequest request);
}
