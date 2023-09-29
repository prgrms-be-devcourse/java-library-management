package com.programmers.app.timer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.programmers.app.file.FileManager;

public class NormalTimerManager implements TimerManger {

    private final FileManager<Queue<Timer>, Queue<Timer>> fileManager;
    private Queue<Timer> arrangementTimers;

    public NormalTimerManager(FileManager<Queue<Timer>, Queue<Timer>> fileManager) throws IOException {
        this.fileManager = fileManager;
        this.arrangementTimers = fileManager.loadDataFromFile();
    }

    @Override
    public void add(Timer timer) {
        arrangementTimers.add(timer);
    }

    @Override
    public List<Long> popArrangedBooks(LocalDateTime now) {
        List<Long> arrangedBooks = new ArrayList<>();

        while(!arrangementTimers.isEmpty() && arrangementTimers.peek().isCompleted(now)) {
            arrangedBooks.add(arrangementTimers.remove().getBookNumber());
        }

        return arrangedBooks;
    }

    @Override
    public boolean remove(long bookNumber) {
        return arrangementTimers.removeIf(timer -> timer.isMatching(bookNumber));
    }

    @Override
    public void save() {
        fileManager.save(arrangementTimers);
    }
}
