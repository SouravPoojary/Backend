package com.example.demo.dto;

import com.example.demo.resource.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    private int id;
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotBlank(message = "password cannot be blank")
    private String password;
    @NotNull(message = "Role should be mentioned")
    private Role role;
}
