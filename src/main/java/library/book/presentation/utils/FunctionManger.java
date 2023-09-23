package library.book.presentation.utils;

import java.util.function.Consumer;

public enum FunctionManger {

	ONE(FunctionExecutor::executeRegisterBook),
	;

	private final Consumer<FunctionExecutor> callback;

	FunctionManger(final Consumer<FunctionExecutor> callback) {
		this.callback = callback;
	}

	public void call(FunctionExecutor executor) {
		this.callback.accept(executor);
	}
}
