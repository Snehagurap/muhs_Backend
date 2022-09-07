package in.cdac.university.planningBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PlanningBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanningBoardApplication.class, args);
	}

}
