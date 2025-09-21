package pro.java.education.limit.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.java.education.limit.repository.DailyLimitRepository;
import pro.java.education.payment.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SchedulerService {

    private final DailyLimitRepository dailyLimitRepository;
    private final PaymentRepository paymentRepository;

    @Scheduled(cron = "${scheduler.drop_limits_cron}")

    public void resetDailyLimits() {
        dailyLimitRepository.resetDailyLimits(1000000L, LocalDateTime.of(now().toLocalDate(), LocalTime.MIN));
        paymentRepository.deleteAllPayments();
        log.info("Limits and payments was dropped by scheduled service");
    }
}
