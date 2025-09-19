package com.tien.springboot_traning.enums;

import com.tien.springboot_traning.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

//enum có 2 role admin và user
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Roles {
    ADMIN,
    USER;
}
