package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Vehicle")
@Data
public class VehicleDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String compname;
    private String vehicleno;
    @ManyToOne
    @JoinColumn(name = "vehicle_owner_id", referencedColumnName = "id")
    private VehicleOwnerEntity vehicleOwnerEntity;

//    @OneToMany(mappedBy = "vehicleDetailsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ServiceAppointmentEntity> serviceAppointmentEntityList;
}
