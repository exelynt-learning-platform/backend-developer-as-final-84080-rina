
package com.example.booking.config;

import com.example.booking.entity.AppUser;
import com.example.booking.entity.Resource;
import com.example.booking.entity.Role;
import com.example.booking.repository.ResourceRepository;
import com.example.booking.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class SeedDataConfig {

    @Bean
    CommandLineRunner seed(
            UserRepository users,
            ResourceRepository resources,
            PasswordEncoder encoder) {

        return args -> {

            // Create ADMIN user
            if (users.findByUsername("admin").isEmpty()) {

                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("Admin@123"));
                admin.setRole(Role.ADMIN);

                users.save(admin);
            }

            // Create USER user
            if (users.findByUsername("user").isEmpty()) {

                AppUser user = new AppUser();
                user.setUsername("user");
                user.setPassword(encoder.encode("User@123"));
                user.setRole(Role.USER);

                users.save(user);
            }

            // Create sample resources
            if (resources.count() == 0) {

                Resource room = new Resource();
                room.setName("Meeting Room A");
                room.setDescription("10-person meeting room");
                room.setPrice(new BigDecimal("500.00"));
                room.setAvailable(true);

                resources.save(room);

                Resource car = new Resource();
                car.setName("Company Car");
                car.setDescription("Sedan for business travel");
                car.setPrice(new BigDecimal("1500.00"));
                car.setAvailable(true);

                resources.save(car);
            }
        };
    }

}
