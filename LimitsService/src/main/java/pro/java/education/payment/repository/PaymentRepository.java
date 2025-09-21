package pro.java.education.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pro.java.education.payment.model.Payment;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentId(UUID paymentId);

    @Modifying
    @Query(value = "TRUNCATE TABLE payments RESTART IDENTITY", nativeQuery = true)
    void deleteAllPayments();
}
