package com.issougames.test_helios_first;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FizzBuzzServiceTest {

	private final FizzBuzzService service = new FizzBuzzService();

	@Test
	// checks the classic fizzbuzz output
	void generate_classicFizzBuzz_returnsExpectedList() {
		List<String> result = service.generate(new FizzBuzzRequest(3, 5, 15, "fizz", "buzz"));
		assertThat(result).containsExactly(
				"1", "2", "fizz", "4", "buzz", "fizz", "7", "8", "fizz", "buzz",
				"11", "fizz", "13", "14", "fizzbuzz"
		);
	}

	@Test
	// checks limit 1 returns only "1"
	void generate_limitOfOne_returnsSingleElement() {
		List<String> result = service.generate(new FizzBuzzRequest(3, 5, 1, "fizz", "buzz"));
		assertThat(result).containsExactly("1");
	}

	@Test
	// checks same divisors combine into str1 + str2
	void generate_int1EqualsInt2_combinesOnEveryMultiple() {
		List<String> result = service.generate(new FizzBuzzRequest(3, 3, 6, "fizz", "buzz"));
		assertThat(result).containsExactly("1", "2", "fizzbuzz", "4", "5", "fizzbuzz");
	}

	@Test
	// checks int1=1 replaces every value with str1 or str1str2
	void generate_int1EqualsOne_allMultiplesOfInt1() {
		List<String> result = service.generate(new FizzBuzzRequest(1, 3, 6, "fizz", "buzz"));
		assertThat(result).containsExactly("fizz", "fizz", "fizzbuzz", "fizz", "fizz", "fizzbuzz");
	}
}
