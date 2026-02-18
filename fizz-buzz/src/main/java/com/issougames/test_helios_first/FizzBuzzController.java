package com.issougames.test_helios_first;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class FizzBuzzController {

	private static final int MAX_LIMIT = 1000;

	private final FizzBuzzService fizzBuzzService;
	private final StatisticsService statisticsService;

	public FizzBuzzController(FizzBuzzService fizzBuzzService, StatisticsService statisticsService) {
		this.fizzBuzzService = fizzBuzzService;
		this.statisticsService = statisticsService;
	}

	@GetMapping("/fizzbuzz")
	public List<String> fizzBuzz(
			@RequestParam int int1,
			@RequestParam int int2,
			@RequestParam int limit,
			@RequestParam String str1,
			@RequestParam String str2
	) {
		validateRequest(int1, int2, limit, str1, str2);

		FizzBuzzRequest request = new FizzBuzzRequest(int1, int2, limit, str1, str2);
		statisticsService.recordRequest(request);
		return fizzBuzzService.generate(request);
	}

	@GetMapping("/statistics")
	public StatisticsResponse statistics() {
		return statisticsService.getMostFrequentRequest()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No request has been recorded yet"));
	}

	private void validateRequest(int int1, int int2, int limit, String str1, String str2) {
		if (int1 <= 0 || int2 <= 0 || limit <= 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "int1, int2 and limit must be greater than 0");
		}
		if (limit > MAX_LIMIT) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "limit must not exceed " + MAX_LIMIT);
		}
		if (str1 == null || str1.isBlank() || str2 == null || str2.isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "str1 and str2 must not be blank");
		}
	}
}
