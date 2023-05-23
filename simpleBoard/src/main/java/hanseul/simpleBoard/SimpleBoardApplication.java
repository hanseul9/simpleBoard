package hanseul.simpleBoard;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
@RequiredArgsConstructor
public class SimpleBoardApplication {

	private final InitDB initDB;

	@PostConstruct
	public void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		initDB.dataInit();
	}

	public static void main(String[] args) {
		SpringApplication.run(SimpleBoardApplication.class, args);
	}

}
