package com.programmers.infrastructure;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.programmers.domain.enums.BookStatusType;
import com.programmers.domain.status.BookStatus;
import java.io.IOException;

public class BookStatusSerializer extends StdSerializer<BookStatus> {

    public BookStatusSerializer() {
        super(BookStatus.class);
    }

    @Override
    public void serialize(BookStatus value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
        BookStatusType statusName = value.getBookStatusName();
        gen.writeString(statusName.toString());
    }
}

