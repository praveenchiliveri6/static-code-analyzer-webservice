/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
package apllcation;
/*
 * The copyright of this file belongs to Koninklijke Philips N.V., 2019.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    final ApplicationContext ctx=SpringApplication.run(Application.class, args);
  }

}

