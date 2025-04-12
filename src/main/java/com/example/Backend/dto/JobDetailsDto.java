package com.example.Backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class JobDetailsDto {
//    @NotBlank(message = "Job name cannot be empty")
    private String jobName;

//    @NotBlank(message = "Description cannot be empty")
    private String description;

//    @NotNull(message = "Amount is required")
//    @Min(value = 0, message = "Amount must be a positive number")
    private Double amount;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
