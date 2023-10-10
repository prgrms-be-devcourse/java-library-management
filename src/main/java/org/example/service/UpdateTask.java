package org.example.service;

import org.example.domain.Book;
import org.example.domain.BookState;

import java.util.TimerTask;

public class UpdateTask extends TimerTask{
        private Book book;

        public UpdateTask(Book book) {
            this.book = book;
        }

        @Override
        public void run() {
            book.setState(BookState.POSSIBLE);
        }
}
