package com.example.Backend.dto;

import com.example.Backend.entity.JobDetails;
import com.example.Backend.entity.Services;
import com.example.Backend.entity.User;
import com.example.Backend.resource.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDto {
    private Long id;

    @NotNull(message = "Customer ID cannot be null")
    private User customerId;

    @NotNull(message = "Service ID cannot be null")
    private Services serviceId;
    @NotBlank(message = "Vehicle name is required")
    private String vehicleName;

    @NotBlank(message = "Registration number is required")
    @Pattern(regexp = "^[A-Z]{2}-\\d{2}-[A-Z]{2}-\\d{4}$",
            message = "Registration number must be in format AB-12-XY-3456")
    private String regNo;

    @NotBlank(message = "Description  is required")
    private String description;

    @NotBlank(message = "Appointment date is required")
    @Pattern(regexp = "^(202[5-9]|20[3-9]\\d|2100)-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
            message = "Appointment date must be in yyyy-mm-dd format (2025-2100)")
    private String appointmentDate;

    @NotBlank(message = "Appointment time is required")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):([0-5][0-9])$",
            message = "Appointment time must be in HH:mm format (24-hour)")
    private String appointmentTime;
//    @NotBlank(message = "Status cannot be blank")
//    @Pattern(regexp = "Pending|In_Progress|Completed|Canceled", message = "Status must be PENDING, CONFIRMED, or COMPLETED")
    private Status status;
    private JobDetails jobDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Customer ID cannot be null") User getCustomerId() {
        return customerId;
    }

    public void setCustomerId(@NotNull(message = "Customer ID cannot be null") User customerId) {
        this.customerId = customerId;
    }

    public @NotNull(message = "Service ID cannot be null") Services getServiceId() {
        return serviceId;
    }

    public void setServiceId(@NotNull(message = "Service ID cannot be null") Services serviceId) {
        this.serviceId = serviceId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public JobDetails getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(JobDetails jobDetails) {
        this.jobDetails = jobDetails;
    }
}

