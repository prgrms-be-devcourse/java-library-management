package com.programmers.library;

import com.programmers.library.controller.LibraryController;
import com.programmers.library.repository.LibraryFileRepository;
import com.programmers.library.repository.LibraryMemoryRepository;
import com.programmers.library.repository.LibraryRepository;
import com.programmers.library.service.LibraryService;
import com.programmers.library.utils.Mode;

public class LibraryApplication {
    public static void main(String[] args) {
        // 모드 선택
        Mode modeType = Mode.selectMode();

        // 모드별 Repository 의존 주입
        LibraryRepository libraryRepository;
        if(modeType == Mode.NORMAL) {
            libraryRepository = new LibraryFileRepository();
        }
        else {
            libraryRepository = new LibraryMemoryRepository();
        }

        // Service, Controller 의존 주입
        LibraryService libraryService = new LibraryService(libraryRepository);
        LibraryController libraryController = new LibraryController(libraryService);

        // 메뉴 시작
        libraryController.run();
    }

}