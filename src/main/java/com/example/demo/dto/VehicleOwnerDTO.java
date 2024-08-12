package com.example.demo.dto;

import com.example.demo.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class VehicleOwnerDTO {
    private long id;
    @NotBlank(message = "Owner name cannot be blank")
    private  String owner_name;
    @NotBlank(message = "Owner mobno cannot be blank")
    @Pattern(regexp ="^\\+?[0-9. ()-]{7,25}$",message = "Mob no cannot be invalid")
    private String owner_mobno;
    @Email(message = "Email cannot be empty")
    private String owner_email;
    @NotNull(message = "Owner Address cannot be empty")
    private Address address;
//        private List<ServiceAppointmentDTO> serviceAppointments;
//        private List<VehicleDetailsDTO> vehicles;
//        private List<NotificationDTO> notifications;
//        private List<FeedbackDTO> feedbacks;
//        private List<ServiceHistoryDTO> serviceHistories;
//        private List<ServiceTypeOptionDTO> serviceTypeOptions;
    }



