package pro.java.education.limit.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pro.java.education.payment.model.Payment;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "daily_limits")
public class DailyLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "limit_value")
    private Long limitValue;

    @Column(nullable = false, name = "reset_date")
    private LocalDateTime resetDate;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Payment> payments;
}
