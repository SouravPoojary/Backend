package com.example.Backend.dto;


import com.example.Backend.resource.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SCenterDto {
    private Long id;

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters")
    private String fullname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Contact is required")
    @Size(min = 10, max = 15, message = "Contact must be between 10 and 15 characters")
    private String contact;

    @NotBlank(message = "Shop name is required")
    private String shopname;

    @NotBlank(message = "Address is required")
    private String address;

//    private Role role = Role.SERVICE_CENTER; // Default role is SERVICE_CENTER

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Full name is required") @Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters") String getFullname() {
        return fullname;
    }

    public void setFullname(@NotBlank(message = "Full name is required") @Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters") String fullname) {
        this.fullname = fullname;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Invalid email format") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters long") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters long") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Contact is required") @Size(min = 10, max = 15, message = "Contact must be between 10 and 15 characters") String getContact() {
        return contact;
    }

    public void setContact(@NotBlank(message = "Contact is required") @Size(min = 10, max = 15, message = "Contact must be between 10 and 15 characters") String contact) {
        this.contact = contact;
    }

    public @NotBlank(message = "Shop name is required") String getShopname() {
        return shopname;
    }

    public void setShopname(@NotBlank(message = "Shop name is required") String shopname) {
        this.shopname = shopname;
    }

    public @NotBlank(message = "Address is required") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Address is required") String address) {
        this.address = address;
    }

//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
}
