package com.example.Backend.entity;


import com.example.Backend.resource.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    private String email;
    @JsonIgnore
    private String password;
    private String contact;
    private String address;

    // SCenter-specific field (nullable for customers)
    private String shopname;

    @Enumerated(value = EnumType.STRING)
    private Role role; // CUSTOMER, SERVICE_CENTER, ADMIN

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static UserBuilder builder(){
        return  new UserBuilder();
    }

    public static class UserBuilder{
        private Long id;
        private String fullname;
        private String email;
        private String password;
        private String contact;
        private String address;
        private String shopname;
        private Role role;

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder fullname(String fullname) {
            this.fullname = fullname;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder contact(String contact) {
            this.contact = contact;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }

        public UserBuilder shopname(String shopname) {
            this.shopname = shopname;
            return this;
        }

        public UserBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(this.id);
            user.setFullname(this.fullname);
            user.setEmail(this.email);
            user.setPassword(this.password);
            user.setContact(this.contact);
            user.setAddress(this.address);
            user.setShopname(this.shopname);
            user.setRole(this.role);
            return user;
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isCustomer() {
        return role == Role.CUSTOMER;
    }

    public boolean isServiceCenter() {
        return role == Role.SERVICE_CENTER;
    }



}
