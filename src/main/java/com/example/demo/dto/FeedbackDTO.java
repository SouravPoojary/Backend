package com.example.demo.dto;

import com.example.demo.entity.ServiceTypeEntity;
import com.example.demo.entity.TechnicianDetailsEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FeedbackDTO {
    private long id;
    @NotBlank(message = "Comment cannot be blank")
    private String comment;
    @NotBlank(message = "Rating cannot be blank")
    private String rating;
    @NotNull(message = "Service type details cannot be null")
    private ServiceTypeEntity serviceTypeEntity;
    @NotNull(message = "Technician details cannot be null")
    private TechnicianDetailsEntity technicianDetailsEntity;
}
