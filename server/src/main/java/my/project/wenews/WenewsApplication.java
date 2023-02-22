package my.project.wenews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication
public class WenewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WenewsApplication.class, args);
    }

}
