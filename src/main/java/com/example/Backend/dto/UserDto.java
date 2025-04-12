//package com.example.Backend.dto;
//
//import com.example.Backend.resource.Role;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
//public class UserDto {
//        private Long id;
//
//    @NotBlank(message = "Name cannot be blank")
//    private String name;
//
//    @Email(message = "Invalid email format")
//    @NotBlank(message = "Email cannot be blank")
//    private String email;
//
//    @NotNull(message = "Password cannot be null")
//private String password;
//
//    @NotNull(message = "Role is required")
////    @Enumerated(EnumType.STRING)
////    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    private Role role; // CUSTOMER, SERVICE_CENTER, ADMIN
//
//    @NotBlank(message = "Contact cannot be blank")
//    private String contact;
//
//
//    @NotBlank(message = "Address cannot be null")
//    private String address;
//
//    @NotBlank(message = "Shop name cannot be null for service centers")
//   @JsonInclude(JsonInclude.Include.NON_NULL) // Exclude if null
//    private String shopName;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public @NotBlank(message = "Name cannot be blank") String getName() {
//        return name;
//    }
//
//    public void setName(@NotBlank(message = "Name cannot be blank") String name) {
//        this.name = name;
//    }
//
//    public @Email(message = "Invalid email format") @NotBlank(message = "Email cannot be blank") String getEmail() {
//        return email;
//    }
//
//    public void setEmail(@Email(message = "Invalid email format") @NotBlank(message = "Email cannot be blank") String email) {
//        this.email = email;
//    }
//
//    public @NotNull(message = "Password cannot be null") String getPassword() {
//        return password;
//    }
//
//    public void setPassword(@NotNull(message = "Password cannot be null") String password) {
//        this.password = password;
//    }
//
//    public @NotBlank(message = "Role is required") Role getRole() {
//        return role;
//    }
//
//    public void setRole(@NotBlank(message = "Role is required") Role role) {
//        this.role = role;
//    }
//
//    public @NotBlank(message = "Contact cannot be blank") String getContact() {
//        return contact;
//    }
//
//    public void setContact(@NotBlank(message = "Contact cannot be blank") String contact) {
//        this.contact = contact;
//    }
//
//    public @NotBlank(message = "Address cannot be null") String getAddress() {
//        return address;
//    }
//
//    public void setAddress(@NotBlank(message = "Address cannot be null") String address) {
//        this.address = address;
//    }
//
//    public @NotBlank(message = "Shop name cannot be null for service centers") String getShopName() {
//        return shopName;
//    }
//
//    public void setShopName(@NotBlank(message = "Shop name cannot be null for service centers") String shopName) {
//        this.shopName = shopName;
//    }
//}
//
