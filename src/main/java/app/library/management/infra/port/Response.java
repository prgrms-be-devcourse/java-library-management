package app.library.management.infra.port;

import app.library.management.core.domain.BookStatus;
import app.library.management.infra.port.dto.response.BookResponseDto;
import app.library.management.core.service.response.dto.status.ResponseState;
import app.library.management.core.service.response.dto.status.Stage;

import java.util.List;

public interface Response {

    void saveBookResponse();
    void findAllResponse(List<BookResponseDto> bookResponseDtoList);
    void findByTitleResponse(List<BookResponseDto> bookResponseDtoList);
    void rentByIdResponse(ResponseState responseState, Stage stage, BookStatus bookStatus);
    void returnByIdResponse(ResponseState responseState, Stage stage, BookStatus bookStatus);
    void deleteByIdResponse(ResponseState responseState, Stage stage, BookStatus bookStatus);
    void reportLostByIdResponse(ResponseState responseState, Stage stage, BookStatus bookStatus);

}
