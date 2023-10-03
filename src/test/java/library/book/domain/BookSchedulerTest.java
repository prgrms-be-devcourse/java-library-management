package library.book.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import library.book.application.utils.BookScheduler;

@DisplayName("[BookScheduler Test] - Domain")
public class BookSchedulerTest {

	@Test
	@DisplayName("[스케줄러에 등록된 작업이 정해진 시간 후에 실행된다]")
	void registerTaskTest() throws InterruptedException {
		//given
		List<Integer> list = new ArrayList<>();
		Runnable task = () -> list.add(0);

		//when
		BookScheduler.registerTask(task, 0L);

		//then
		Thread.sleep(1);
		assertThat(list).hasSize(1);
	}
}
