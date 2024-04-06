package de.tuxbe.testtechnique.web.dto;

import java.time.LocalDate;

public record PersonneDto(String numIdentityUnique, String firstName, String lastName, LocalDate birthday) {
}
