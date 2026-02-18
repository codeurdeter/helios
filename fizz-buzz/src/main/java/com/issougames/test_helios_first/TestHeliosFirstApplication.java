package com.issougames.test_helios_first;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestHeliosFirstApplication {

	public static void main(String[] args) {
		ensureDataDirectoryExists();
		SpringApplication.run(TestHeliosFirstApplication.class, args);
	}

	private static void ensureDataDirectoryExists() {
		try {
			Files.createDirectories(Path.of("data"));
		} catch (IOException exception) {
			throw new IllegalStateException("Cannot create data directory for sqlite database", exception);
		}
	}

}
