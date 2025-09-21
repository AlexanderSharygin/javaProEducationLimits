package pro.java.education.limit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pro.java.education.limit.dto.DailyLimitDto;
import pro.java.education.limit.dto.PaymentRequestDto;
import pro.java.education.limit.service.LimitService;

import java.util.UUID;

@RestController
@RequestMapping(path = "V1/limits")
@RequiredArgsConstructor
public class DailyLimitsController {
    private final LimitService limitService;

    @GetMapping("/{userId}")
    public DailyLimitDto getUserLimit(@PathVariable("userId") Long userId) {
        return limitService.getDailyLimitByUserId(userId);
    }

    @PostMapping
    public DailyLimitDto performPaymentRequest(@RequestBody @Valid PaymentRequestDto paymentRequest) {
        return limitService.performPaymentRequest(paymentRequest);
    }

    @PatchMapping("/approve/{paymentId}")
    public void approvePayment(@PathVariable("paymentId") UUID paymentId) {
        limitService.approvePayment(paymentId);
    }

    @PatchMapping("/reject/{paymentId}")
    public void rejectPayment(@PathVariable("paymentId") UUID paymentId) {
        limitService.rejectPayment(paymentId);
    }
}