package pro.java.education.payment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "payment_id")
    private UUID paymentId;

    @Column(nullable = false, unique = true, name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true, name = "amount")
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, name = "status")
    private PaymentStatus state;
}
