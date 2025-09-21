package pro.java.education.limit.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentRequestDto(@NotNull Long userId,
                                @Positive Long amount) {
}
