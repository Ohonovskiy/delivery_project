package application.pet.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * The main entry point of the application.
 */
@SpringBootApplication
@PropertySource("classpath:keys.env")
public class DeliveryApplication {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }

}
