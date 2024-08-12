package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "ServiceType")
public class ServiceTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String servicename;
    private String servicedetails;
//    private String price;

    @ManyToOne
    @JoinColumn(name = "vehicle_details",referencedColumnName = "id")
    private VehicleDetailsEntity vehicleDetailsEntity;



//    @OneToMany(mappedBy = "serviceTypeEntities")
//    private List<ServiceAppointmentEntity> serviceAppointmentEntityList;

//    @ManyToMany(mappedBy = "serviceTypeEntities")
//    private List<VehicleOwnerEntity> vehicleOwnerEntityList;
}

