package Core;

//import Mock.MockDataInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 *
 * @author jakubvacek
 */
@SpringBootApplication(scanBasePackages = {"Controller","Service","Core/Security"})
public class Application {
    public static void main(String[] args) {
        MockDataInitializer mdi = new MockDataInitializer();
        SpringApplication.run(Application.class, args);
    }
}



