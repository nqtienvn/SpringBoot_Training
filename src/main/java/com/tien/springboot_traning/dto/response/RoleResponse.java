package com.tien.springboot_traning.dto.response;

import com.tien.springboot_traning.entity.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RoleResponse {
    String name;
    String description;
    Set<Permission> permissions;
}
