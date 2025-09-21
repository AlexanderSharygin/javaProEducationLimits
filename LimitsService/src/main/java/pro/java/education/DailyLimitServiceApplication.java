package pro.java.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DailyLimitServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyLimitServiceApplication.class, args);
    }
}