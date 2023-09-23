package library.book.application;

import library.book.application.dto.request.RegisterBookRequest;

public interface BookManageService {

	void registerBook(RegisterBookRequest request);
}
