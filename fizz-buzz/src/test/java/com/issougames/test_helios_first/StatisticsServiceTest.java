package com.issougames.test_helios_first;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StatisticsServiceTest {

	@Autowired
	private StatisticsService service;

	@BeforeEach
	void setUp() {
		service.clear();
	}

	@Test
	// checks empty stats when no request was recorded
	void getMostFrequentRequest_noRequests_returnsEmpty() {
		assertThat(service.getMostFrequentRequest()).isEmpty();
	}

	@Test
	// checks one request is saved with count 1
	void recordRequest_singleRequest_returnsItWithCountOne() {
		FizzBuzzRequest request = new FizzBuzzRequest(3, 5, 15, "fizz", "buzz");
		service.recordRequest(request);

		Optional<StatisticsResponse> result = service.getMostFrequentRequest();
		assertThat(result).isPresent();
		assertThat(result.get().count()).isEqualTo(1);
		assertThat(result.get().int1()).isEqualTo(3);
		assertThat(result.get().int2()).isEqualTo(5);
	}

	@Test
	// checks most frequent request is returned first
	void recordRequest_multipleDistinctRequests_returnsMostFrequent() {
		FizzBuzzRequest frequent = new FizzBuzzRequest(3, 5, 15, "fizz", "buzz");
		FizzBuzzRequest rare = new FizzBuzzRequest(2, 7, 20, "foo", "bar");

		service.recordRequest(frequent);
		service.recordRequest(frequent);
		service.recordRequest(frequent);
		service.recordRequest(rare);

		Optional<StatisticsResponse> result = service.getMostFrequentRequest();
		assertThat(result).isPresent();
		assertThat(result.get().count()).isEqualTo(3);
		assertThat(result.get().int1()).isEqualTo(3);
	}

	@Test
	// checks concurrent writes are all counted
	void recordRequest_concurrentAccess_allRequestsCounted() throws InterruptedException {
		FizzBuzzRequest request = new FizzBuzzRequest(3, 5, 15, "fizz", "buzz");
		int threadCount = 8;
		int iterationsPerThread = 125;
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		CountDownLatch startLatch = new CountDownLatch(1);

		for (int t = 0; t < threadCount; t++) {
			executor.submit(() -> {
				try {
					startLatch.await();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				for (int i = 0; i < iterationsPerThread; i++) {
					service.recordRequest(request);
				}
			});
		}

		startLatch.countDown();
		executor.shutdown();
		assertThat(executor.awaitTermination(30, TimeUnit.SECONDS)).isTrue();

		Optional<StatisticsResponse> result = service.getMostFrequentRequest();
		assertThat(result).isPresent();
		assertThat(result.get().count()).isEqualTo(1000);
	}

	@Test
	// checks clear removes all stored statistics
	void clear_afterRecording_resetsToEmpty() {
		service.recordRequest(new FizzBuzzRequest(3, 5, 15, "fizz", "buzz"));
		service.clear();
		assertThat(service.getMostFrequentRequest()).isEmpty();
	}
}
