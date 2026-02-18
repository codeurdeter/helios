package com.issougames.test_helios_first;

import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatisticsService {

	private final RequestStatisticRepository repository;

	public StatisticsService(RequestStatisticRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public void recordRequest(FizzBuzzRequest request) {
		RequestStatistic statistic = repository
				.findByInt1AndInt2AndRequestLimitAndStr1AndStr2(
						request.int1(), request.int2(), request.limit(),
						request.str1(), request.str2());

		if (statistic == null) {
			statistic = new RequestStatistic(
					request.int1(), request.int2(), request.limit(),
					request.str1(), request.str2());
		}

		statistic.incrementCount();
		repository.save(statistic);
	}

	@Transactional(readOnly = true)
	public Optional<StatisticsResponse> getMostFrequentRequest() {
		return repository.findTopRequests(PageRequest.of(0, 1))
				.stream()
				.findFirst()
				.map(entity -> new StatisticsResponse(
						entity.getInt1(),
						entity.getInt2(),
						entity.getRequestLimit(),
						entity.getStr1(),
						entity.getStr2(),
						entity.getHitCount()));
	}

	@Transactional
	void clear() {
		repository.deleteAllInBatch();
	}
}
