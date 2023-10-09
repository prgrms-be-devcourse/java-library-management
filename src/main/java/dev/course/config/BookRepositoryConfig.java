package dev.course.config;

import dev.course.domain.AppConstants;
import dev.course.exception.FileIOFailureException;
import dev.course.manager.JSONFileManager;
import dev.course.repository.BookRepository;
import dev.course.repository.GeneralBookRepository;
import dev.course.repository.TestBookRepository;

import java.io.IOException;

public class BookRepositoryConfig {

    private BookRepository bookRepository;

    /**
     * 구현하면서 가장 애먹었던 부분,,,
     * 입력값 modeId에 따라 적절한 구현체를 전달함
     * setter 를 제거할 수 있는 방안을 생각해내지 못했음,, -> 생성자로 대체함
     */

    public BookRepositoryConfig(int modeId) {
        if (modeId == AppConstants.GENERAL) {
            this.bookRepository = getGeneral();
        } else if (modeId == AppConstants.TEST) {
            this.bookRepository = getTest();
        }
    }

    // 테스트 코드에서 Override 해서 Mode 구현체를 주입함
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookRepository getBookRepository() {
        return this.bookRepository;
    }

    public BookRepository getGeneral() {
        return new GeneralBookRepository(getJSONFileManager(), AppConstants.FILEPATH);
    }

    public BookRepository getTest() {
        return new TestBookRepository();
    }

    public JSONFileManager getJSONFileManager() {

        try {
            return new JSONFileManager();
        } catch (IOException e) {
            throw new FileIOFailureException("[System] 파일을 읽어오는데 실패했습니다.\n");
        }
    }
}
