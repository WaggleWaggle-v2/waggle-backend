package unius.application_member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "unius.application_member",
        "unius.domain_bookshelf",
        "unius.domain_user",
        "unius.domain_oauth",
        "unius.independent_s3",
        "unius.independent_jpa",
        "unius.system_exception",
        "unius.system_jwt",
        "unius.system_oauth",
        "unius.system_rest_template"
})
@EnableJpaRepositories(basePackages = {
        "unius.domain_bookshelf",
        "unius.domain_user",
        "unius.domain_oauth"
})
@EntityScan(basePackages = {
        "unius.domain_bookshelf",
        "unius.domain_user",
        "unius.domain_oauth"
})
public class MemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }

}
