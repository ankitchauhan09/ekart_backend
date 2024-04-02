package com.example.ekart_backend;

import com.example.ekart_backend.config.web.AppConstants;
import com.example.ekart_backend.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.ekart_backend.entities.Role;

import java.util.List;

@SpringBootApplication
public class EKartBackendApplication implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    private final Logger logger = LoggerFactory.getLogger(EKartBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EKartBackendApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    ;

    @Override
    public void run(String... args) throws Exception {

        try {

            Role role = new Role();
            role.setRoleId(AppConstants.ROLE_USER);
            role.setRoleName("USER");

            Role role1 = new Role();
            role1.setRoleId(AppConstants.ROLE_ADMIN);
            role1.setRoleName("ADMIN");

            List<Role> roleResult = this.roleRepo.saveAll(List.of(role, role1));
            this.logger.info("Roles created : {}", roleResult);
        } catch (Exception e) {
            System.out.printf(e.getMessage());
        }
    }

}
