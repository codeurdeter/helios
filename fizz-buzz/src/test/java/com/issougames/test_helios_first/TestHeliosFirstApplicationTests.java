package com.issougames.test_helios_first;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestHeliosFirstApplicationTests {

	@Autowired
	private StatisticsService statisticsService;

	@LocalServerPort
	private int port;

	private final HttpClient httpClient = HttpClient.newHttpClient();

	@BeforeEach
	void resetStatistics() {
		statisticsService.clear();
	}

	@Test
	// checks the api returns the expected fizzbuzz list
	void fizzBuzzEndpointReturnsExpectedList() throws Exception {
		HttpResponse<String> response = get("/api/fizzbuzz", Map.of(
				"int1", 3,
				"int2", 5,
				"limit", 15,
				"str1", "fizz",
				"str2", "buzz"
		));

		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
		JSONAssert.assertEquals(
				"[\"1\",\"2\",\"fizz\",\"4\",\"buzz\",\"fizz\",\"7\",\"8\",\"fizz\",\"buzz\",\"11\",\"fizz\",\"13\",\"14\",\"fizzbuzz\"]",
				response.body(),
				true
		);
	}

	@Test
	// checks statistics returns the most used request
	void statisticsEndpointReturnsMostFrequentRequest() throws Exception {
		for (int i = 0; i < 3; i++) {
			HttpResponse<String> response = get("/api/fizzbuzz", Map.of(
					"int1", 3,
					"int2", 5,
					"limit", 150,
					"str1", "fizz",
					"str2", "buzz"
			));
			assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
		}

		HttpResponse<String> secondRequest = get("/api/fizzbuzz", Map.of(
				"int1", 2,
				"int2", 7,
				"limit", 20,
				"str1", "foo",
				"str2", "bar"
		));
		assertThat(secondRequest.statusCode()).isEqualTo(HttpStatus.OK.value());

		HttpResponse<String> statistics = get("/api/statistics");
		assertThat(statistics.statusCode()).isEqualTo(HttpStatus.OK.value());
		JSONAssert.assertEquals(
				"{\"int1\":3,\"int2\":5,\"limit\":150,\"str1\":\"fizz\",\"str2\":\"buzz\",\"count\":3}",
				statistics.body(),
				true
		);
	}

	@Test
	// checks api rejects int1 when it is 0
	void fizzBuzzEndpointRejectsInvalidValues() throws Exception {
		HttpResponse<String> response = get("/api/fizzbuzz", Map.of(
				"int1", 0,
				"int2", 5,
				"limit", 15,
				"str1", "fizz",
				"str2", "buzz"
		));
		assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	// checks api rejects int2 when it is negative
	void fizzBuzzEndpointRejectsNegativeInt2() throws Exception {
		HttpResponse<String> response = get("/api/fizzbuzz", Map.of(
				"int1", 3,
				"int2", -1,
				"limit", 15,
				"str1", "fizz",
				"str2", "buzz"
		));
		assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	// checks api rejects limit when it is negative
	void fizzBuzzEndpointRejectsNegativeLimit() throws Exception {
		HttpResponse<String> response = get("/api/fizzbuzz", Map.of(
				"int1", 3,
				"int2", 5,
				"limit", -5,
				"str1", "fizz",
				"str2", "buzz"
		));
		assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	// checks api rejects str1 when it is blank
	void fizzBuzzEndpointRejectsBlankStr1() throws Exception {
		HttpResponse<String> response = get("/api/fizzbuzz", Map.of(
				"int1", 3,
				"int2", 5,
				"limit", 15,
				"str1", " ",
				"str2", "buzz"
		));
		assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	// checks api rejects str2 when it is blank
	void fizzBuzzEndpointRejectsBlankStr2() throws Exception {
		HttpResponse<String> response = get("/api/fizzbuzz", Map.of(
				"int1", 3,
				"int2", 5,
				"limit", 15,
				"str1", "fizz",
				"str2", " "
		));
		assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	// checks api rejects limit above max value
	void fizzBuzzEndpointRejectsLimitExceedingMax() throws Exception {
		HttpResponse<String> response = get("/api/fizzbuzz", Map.of(
				"int1", 3,
				"int2", 5,
				"limit", 1001,
				"str1", "fizz",
				"str2", "buzz"
		));
		assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	// checks statistics returns 404 when there is no data
	void statisticsEndpointReturnsNotFoundWhenNoRequestWasRecorded() throws Exception {
		HttpResponse<String> response = get("/api/statistics");
		assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	private HttpResponse<String> get(String path) throws Exception {
		URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + port + path)
				.build(true)
				.toUri();
		return sendGet(uri);
	}

	private HttpResponse<String> get(String path, Map<String, ?> queryParams) throws Exception {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:" + port + path);
		for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}
		URI uri = builder.encode().build().toUri();
		return sendGet(uri);
	}

	private HttpResponse<String> sendGet(URI uri) throws Exception {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(uri)
				.GET()
				.build();
		return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	}

}
