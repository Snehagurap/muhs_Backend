package in.cdac.university.committee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CommitteeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommitteeApplication.class, args);
	}

}
