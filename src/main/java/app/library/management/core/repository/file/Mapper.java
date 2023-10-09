package app.library.management.core.repository.file;

import app.library.management.core.domain.Book;

public class Mapper {

    public static Book BookVOToBook(BookVO bookVO) {
        return new Book(bookVO.getId(), bookVO.getTitle(), bookVO.getAuthor(),
                bookVO.getPages(), bookVO.getStatus(), bookVO.getLastModifiedTime());
    }

    public static BookVO BookToBookVO(Book book) {
        return new BookVO(book.getId(), book.getTitle(), book.getAuthor(),
                book.getPages(), book.getStatus(), book.getLastModifiedTime());
    }
}
