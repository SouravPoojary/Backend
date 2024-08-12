package com.example.demo.entity;

import com.example.demo.Address;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Technician Detais")
@Data
public class TechnicianDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    private  String tec_name;
    private String tec_mobno;
    @Embedded
    private Address address;

//    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
//    @JoinColumn(name = "technician_id")
//    private List<ServiceAppointmentEntity> serviceAppointmentEntities;

//    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "technicianDetailsEntity")
//    private List<TechnicianStatusEntity> technicianStatusEntities;
}
