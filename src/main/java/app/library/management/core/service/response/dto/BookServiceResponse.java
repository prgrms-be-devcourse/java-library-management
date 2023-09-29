package app.library.management.core.service.response.dto;

import app.library.management.core.domain.BookStatus;
import app.library.management.core.service.response.dto.status.Stage;
import app.library.management.core.service.response.dto.status.ResponseState;

public class BookServiceResponse {

    private ResponseState responseState;
    private Stage stage;
    private BookStatus bookStatus;

    public BookServiceResponse(ResponseState responseState, Stage stage, BookStatus bookStatus) {
        this.responseState = responseState;
        this.stage = stage;
        this.bookStatus = bookStatus;
    }

    public ResponseState getResponseState() {
        return responseState;
    }

    public Stage getStage() {
        return stage;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }
}
