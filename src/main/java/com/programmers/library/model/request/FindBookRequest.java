package com.programmers.library.model.request;

public class FindBookRequest {
	private String title;

	public FindBookRequest(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
