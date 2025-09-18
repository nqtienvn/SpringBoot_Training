package com.tien.springboot_traning.config;

import com.tien.springboot_traning.enums.Roles;
import com.tien.springboot_traning.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.tien.springboot_traning.entity.User;
import java.util.HashSet;

@Configuration
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> { // ẩn danh của method run(ApplicationArgument) (method implement của applicationRunner)
            var roles = new HashSet<String>();
            roles.add(Roles.ADMIN.name());
            if (userRepository.findUserByName("admin").isEmpty()) {
              User user = User.builder()
                      .name("admin")
                      .roles(roles)
                      .password(passwordEncoder.encode("admin"))
                      .build();
                userRepository.save(user);
                log.warn("tôi vừa tạo user admin với mật khẩu mặc định là 'admin', đổi mật khẩu đi bạn");
            }
        };
    }
}
