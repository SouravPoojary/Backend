package com.example.demo.dto;

import com.example.demo.Address;
import com.example.demo.entity.TechnicianStatusEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class TechnicianDTO {
    private  long id;
    @NotBlank(message ="Name cannot be blank")
    private  String tec_name;
    @NotBlank(message ="must match \"^\\+?[0-9. ()-]{7,25}$\"")
    @Pattern(regexp ="^\\+?[0-9. ()-]{7,25}$")
    private String tec_mobno;
    @NotNull(message ="address cannot be null")
    private Address address;
//    private List<ServiceAppointmentDTO> serviceAppointments;
//    private List<TechnicianStatusEntity> statusUpdates;
}
