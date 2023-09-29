package app.library.management.core.domain.util;

import app.library.management.core.domain.Book;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 도서 상태를 5분 후에
 * ORGANIZING(정리중) -> AVAILABLE(대여 가능) 으로 바꿔주는 기능을 함
 */
public interface StatusManager {

    int POOL_SIZE = 256;
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(POOL_SIZE);

    void execute(Book book);
}
