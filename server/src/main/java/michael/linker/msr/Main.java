package michael.linker.msr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application entry point.
 */
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class Main {
	/**
	 * Main function for app starts.
	 *
	 * @param args console arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
