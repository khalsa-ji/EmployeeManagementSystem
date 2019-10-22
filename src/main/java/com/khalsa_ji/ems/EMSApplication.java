//	Waheguru Ji!

package com.khalsa_ji.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The class {@code EMSApplication} is declared as a <strong>Spring Boot Application</strong> class
 * and provides a method as a main entry point for the application, <strong>Employee Management System</strong>.
 *
 * @author Ravikiran Singh
 * @version 1.0
 * @since 1.0
 * @see SpringApplication
 * @see SpringBootApplication
 * @see io.swagger.models.Swagger
 * @see EnableSwagger2
 */

@SpringBootApplication
@EnableSwagger2
public class EMSApplication {
    /**
     * Method to provide a main entry point of this <em>Spring Boot Application</em> -
     * <strong>Employee Management System</strong>.
     *
     * @param args Command line arguments
     */

    public static void main(String[] args) {
        SpringApplication.run(EMSApplication.class, args);
    }
}
