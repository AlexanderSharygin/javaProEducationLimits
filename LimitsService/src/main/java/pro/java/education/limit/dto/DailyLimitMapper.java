package pro.java.education.limit.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pro.java.education.limit.model.DailyLimit;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class DailyLimitMapper {

    public DailyLimitDto toLimitDtoFromLimit(DailyLimit dailyLimit) {
        return new DailyLimitDto(dailyLimit.getId(), dailyLimit.getLimitValue(), dailyLimit.getResetDate(), null);
    }
}