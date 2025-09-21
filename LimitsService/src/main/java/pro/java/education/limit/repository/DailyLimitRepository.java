package pro.java.education.limit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.java.education.limit.model.DailyLimit;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DailyLimitRepository extends JpaRepository<DailyLimit, Long> {

    Optional<DailyLimit> findByUserId(Long userId);

    @Modifying
    @Query("UPDATE DailyLimit ul SET ul.limitValue=:limit, ul.resetDate = :resetDate")
    void resetDailyLimits(@Param("limit") Long limit, @Param("resetDate") LocalDateTime resetDate);
}
