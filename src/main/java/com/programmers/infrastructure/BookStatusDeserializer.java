package com.programmers.infrastructure;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.programmers.domain.enums.BookStatusType;
import com.programmers.domain.status.BookStatus;
import java.io.IOException;

public class BookStatusDeserializer extends StdDeserializer<BookStatus> {

    public BookStatusDeserializer() {
        super(BookStatus.class);
    }

    @Override
    public BookStatus deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String statusName = jp.readValueAs(String.class);
        return BookStatusType.valueOf(statusName).makeStatus();
    }
}

