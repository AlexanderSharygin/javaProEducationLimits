package pro.java.education.limit.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public record DailyLimitDto(@NotNull Long id, @NotNull @Positive Long limitValue, LocalDateTime resetDate,
                            UUID paymentId) {
}
