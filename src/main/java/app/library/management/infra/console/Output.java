package app.library.management.infra.console;

import app.library.management.core.domain.BookStatus;
import app.library.management.infra.port.dto.response.BookResponseDto;
import app.library.management.infra.port.Response;
import app.library.management.core.service.response.dto.status.ResponseState;
import app.library.management.core.service.response.dto.status.Stage;

import java.util.List;

public class Output implements Response {

    private static final String NOT_FOUND_EXCEPTION = "존재하지 않는 도서번호 입니다.";


    @Override
    public void saveBookResponse() {
        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
    }

    @Override
    public void findAllResponse(List<BookResponseDto> bookResponseDtoList) {
        for (BookResponseDto book : bookResponseDtoList) {
            System.out.println(book);
        }
        System.out.println("[System] 도서 목록 끝\n");
    }

    @Override
    public void findByTitleResponse(List<BookResponseDto> bookResponseDtoList) {
        for (BookResponseDto book : bookResponseDtoList) {
            System.out.println(book);
        }
        System.out.println("[System] 검색된 도서 끝\n");
    }

    @Override
    public void rentByIdResponse(ResponseState responseState, Stage stage, BookStatus bookStatus) {
        if (responseState == ResponseState.SUCCESS) {
            System.out.println(getSuccessMessage(stage.getTitle()));
        }
        else if (responseState == ResponseState.NOTFOUND_EXCEPTION) {
            System.out.println(NOT_FOUND_EXCEPTION);
        }
        else if (responseState == ResponseState.VALIDATION_EXCEPTION) {
            System.out.println(getValidationExceptionMessage(bookStatus.getTitle()));
        }
    }

    @Override
    public void returnByIdResponse(ResponseState responseState, Stage stage, BookStatus bookStatus) {
        if (responseState == ResponseState.SUCCESS) {
            System.out.println(getSuccessMessage(stage.getTitle()));
        }
        else if (responseState == ResponseState.NOTFOUND_EXCEPTION) {
            System.out.println(NOT_FOUND_EXCEPTION);
        }
        else if (responseState == ResponseState.VALIDATION_EXCEPTION) {
            System.out.println(getValidationExceptionMessage(bookStatus.getTitle()));
        }
    }

    @Override
    public void deleteByIdResponse(ResponseState responseState, Stage stage, BookStatus bookStatus) {
        if (responseState == ResponseState.SUCCESS) {
            System.out.println(getSuccessMessage(stage.getTitle()));
        }
        else if (responseState == ResponseState.NOTFOUND_EXCEPTION) {
            System.out.println(NOT_FOUND_EXCEPTION);
        }
    }

    @Override
    public void reportLostByIdResponse(ResponseState responseState, Stage stage, BookStatus bookStatus) {
        if (responseState == ResponseState.SUCCESS) {
            System.out.println(getSuccessMessage(stage.getTitle()));
        }
        else if (responseState == ResponseState.NOTFOUND_EXCEPTION) {
            System.out.println(NOT_FOUND_EXCEPTION);
        }
        else if (responseState == ResponseState.VALIDATION_EXCEPTION) {
            System.out.println(getValidationExceptionMessage(bookStatus.getTitle()));
        }
    }


    private String getSuccessMessage(String currentStage) {
        return "도서가 " + currentStage + " 처리 되었습니다.";
    }

    private String getValidationExceptionMessage(String currentStage) {
        return currentStage + " 인 도서입니다.";
    }


}
