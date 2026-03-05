package com.example.demo.dto;

import java.time.LocalDate;

public record ClientResponse(Long id, String name, LocalDate dob) {
}
