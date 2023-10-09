package com.programmers.library.entity.state;

import java.time.LocalDateTime;

public interface State {
	BookStateType getType();

	State borrow();

	State returned();

	State lost();

	State organize();
}
