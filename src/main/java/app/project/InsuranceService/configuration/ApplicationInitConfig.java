package app.project.InsuranceService.configuration;

import app.project.InsuranceService.entity.Role;
import app.project.InsuranceService.entity.User;

import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.repository.RoleRepository;
import app.project.InsuranceService.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
           if (userRepository.findByUsername("admin").isEmpty()){
               Role adminRole = roleRepository.findById("ADMIN")
                       .orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_FOUND));
               HashSet<Role> roles = new HashSet<>();
               roles.add(adminRole);
               User user = User.builder()
                       .username("admin")
                       .password(passwordEncoder.encode("admin"))
                       .roles(roles)
                       .build();

               userRepository.save(user);
               log.warn("admin user has been created with default password: admin, please change it");

           }
        };
    }
}
