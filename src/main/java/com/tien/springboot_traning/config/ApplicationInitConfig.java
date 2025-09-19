package com.tien.springboot_traning.config;

import com.tien.springboot_traning.entity.Role;
import com.tien.springboot_traning.enums.Roles;
import com.tien.springboot_traning.repository.RoleRepository;
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
//CONFIG KHI CHẠY LẦN ĐẦU(Data rỗng)
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> { // ẩn danh của method run(ApplicationArgument) (method implement của applicationRunner)
            var roles = new HashSet<Role>();
            if(roleRepository.findById(Roles.ADMIN.name()).isEmpty()) {
                roles.add(roleRepository.save(Role.builder().name(Roles.ADMIN.name()).build()));
            }
            else{
                roles.add(roleRepository.findById(Roles.ADMIN.name()).orElseThrow(Exception::new));
            }
            if (userRepository.findUserByName("admin").isEmpty()) {
                User.UserBuilder builder = User.builder();
                builder.name("admin");
                builder.roles(roles);
                builder.password(passwordEncoder.encode("admin"));
                User user = builder
                      .build();
                userRepository.save(user);
                log.warn("tôi vừa tạo user admin với mật khẩu mặc định là 'admin', đổi mật khẩu đi bạn");
            }
        };
    }
}
