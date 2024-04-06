package de.tuxbe.testtechnique.web.dto;

import java.time.LocalDateTime;

public record ErrorMessage(int statutCode, LocalDateTime timestamp, String message, String description) {
}
