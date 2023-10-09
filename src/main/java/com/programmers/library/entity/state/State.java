package com.programmers.library.entity.state;

import com.programmers.library.entity.BookStateType;

public interface State {
	BookStateType getType();

	void borrow();

	void returned();

	void lost();

	State organize();
}
