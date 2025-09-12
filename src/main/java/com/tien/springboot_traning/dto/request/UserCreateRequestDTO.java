package com.tien.springboot_traning.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;


public class UserCreateRequestDTO {
    @Size(min = 3, message = "user name must have at least 3 character")
    private String name;
    @Min(value = 1, message = "age can not < 1")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
