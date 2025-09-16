package com.tien.springboot_traning.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;


public class UserCreateRequestDTO {
    @Size(min = 3, message = "USERNAME_AT_LEST_3")
    private String name;
    @Min(value = 1, message = "age can not < 1")
    private int age;

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
}
