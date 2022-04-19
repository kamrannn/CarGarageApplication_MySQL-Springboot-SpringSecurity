package com.app.cargarage;

import com.app.cargarage.model.User;
import com.app.cargarage.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class CarGarageApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarGarageApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserServiceImpl userService, BCryptPasswordEncoder encoder) {
        return args -> {
            userService.insertUsersOnRuntime(User.builder()
                    .id(1)
                    .fullName("Kamran_Abbasi")
                    .username("admin")
                    .password(encoder.encode("admin"))
                    .role("admin")
                    .build());

            userService.insertUsersOnRuntime(User.builder()
                    .id(2)
                    .fullName("Imran_Khan")
                    .username("cashier")
                    .password(encoder.encode("cashier"))
                    .role("cashier")
                    .build());

            userService.insertUsersOnRuntime(User.builder()
                    .id(3)
                    .fullName("Justin_Bieber")
                    .username("employee")
                    .password(encoder.encode("employee"))
                    .role("employee")
                    .build());
        };
    }
}
