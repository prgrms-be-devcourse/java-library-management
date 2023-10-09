package com.programmers.library.entity.state;

import com.programmers.library.entity.BookStateType;

public interface State {
	BookStateType getType();

	State borrow();

	State returned();

	State lost();

	State organize();
}
