package pro.java.education.limit.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.java.education.exception.model.ConflictException;
import pro.java.education.exception.model.NotFoundException;
import pro.java.education.limit.dto.DailyLimitDto;
import pro.java.education.limit.dto.DailyLimitMapper;
import pro.java.education.limit.dto.PaymentRequestDto;
import pro.java.education.limit.model.DailyLimit;
import pro.java.education.limit.repository.DailyLimitRepository;
import pro.java.education.payment.model.Payment;
import pro.java.education.payment.model.PaymentStatus;
import pro.java.education.payment.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class LimitService {

    private final DailyLimitRepository dailyLimitRepository;
    private final PaymentRepository paymentRepository;
    private final DailyLimitMapper dailyLimitMapper;

    public DailyLimitDto getDailyLimitByUserId(Long userId) {
        Optional<DailyLimit> dailyLimit = dailyLimitRepository.findById(userId);
        if (dailyLimit.isPresent()) {
            return dailyLimitMapper.toLimitDtoFromLimit(dailyLimit.get());
        }
        DailyLimit newDailyLimit = new DailyLimit();
        newDailyLimit.setUserId(userId);
        newDailyLimit.setLimitValue(1000000L);
        newDailyLimit.setResetDate(LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIN));
        dailyLimitRepository.save(newDailyLimit);
        log.info("Limit was added for user = {}", userId);
        return dailyLimitMapper.toLimitDtoFromLimit(newDailyLimit);
    }

    public DailyLimitDto performPaymentRequest(PaymentRequestDto paymentRequestDto) {
        DailyLimit dailyLimit = dailyLimitRepository.findById(paymentRequestDto.userId())
                .orElseThrow(() -> new NotFoundException("Limit is not exist for user " + paymentRequestDto.userId()));
        if (dailyLimit.getLimitValue() < paymentRequestDto.amount()) {
            throw new ConflictException("Limit is too small for user " + paymentRequestDto.userId());
        }
        dailyLimit.setLimitValue(dailyLimit.getLimitValue() - paymentRequestDto.amount());
        UUID paymentId = UUID.randomUUID();
        Payment payment = new Payment();
        payment.setPaymentId(paymentId);
        payment.setAmount(paymentRequestDto.amount());
        payment.setUserId(paymentRequestDto.userId());
        payment.setState(PaymentStatus.PENDING);
        paymentRepository.save(payment);
        dailyLimitRepository.save(dailyLimit);
        log.info("New Payment added for user = {} - limit was reduced", dailyLimit.getUserId());

        return new DailyLimitDto(dailyLimit.getId(), dailyLimit.getLimitValue(), dailyLimit.getResetDate(),
                paymentId);
    }

    public void approvePayment(UUID paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment with id " + paymentId + " not found"));
        payment.setState(PaymentStatus.APPROVED);
        paymentRepository.save(payment);
        log.info("Payment approved with paymentId = {}", paymentId);
    }

    public void rejectPayment(UUID paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment with id " + paymentId + " not found"));
        DailyLimit dailyLimit = dailyLimitRepository.findById(payment.getUserId())
                .orElseThrow(() -> new NotFoundException("Limit is not exist for user " + payment.getUserId()));
        if (payment.getState().equals(PaymentStatus.APPROVED)) {
            throw new ConflictException("Payment is already approved");
        }
        if (payment.getState().equals(PaymentStatus.CANCELED)) {
            throw new ConflictException("Payment is already rejected");
        }
        payment.setState(PaymentStatus.CANCELED);
        dailyLimit.setLimitValue(dailyLimit.getLimitValue() + payment.getAmount());
        paymentRepository.save(payment);
        dailyLimitRepository.save(dailyLimit);
        log.info("Payment rejected with paymentId = {} and limit was refunded", paymentId);
    }
}