package com.example.demo.entity;

import com.example.demo.Address;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Vehicle__Owner")
@Data
public class VehicleOwnerEntity {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private  String owner_name;
    private String owner_mobno;
    private String owner_email;
    @Embedded
    private Address address;


//    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "vehicleOwnerEntity")
//    private List<ServiceAppointmentEntity> serviceAppointmentEntities;
//
// @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "vehicleOwnerEntity")
// private List<NotificationEntity> notificationEntities;
//
// @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "vehicleOwnerEntity")
// private List<FeedbackEntity>feedbackEntities;
//
// @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "vehicleOwnerEntity")
//private List<HistoryEntity>historyEntities;
//
// @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "vehicleOwnerEntity")
// private List<VehicleDetailsEntity>vehicleDetailsEntities;
//
//@ManyToMany
// @JoinTable(
//         name = "vehicle_owner_serv_typ_opt",
//         joinColumns = @JoinColumn(name = "vehicle_owner_id"),
//         inverseJoinColumns = @JoinColumn(name = "service_typ_opt_id"))
// private List<ServiceTypeEntity>serviceTypeEntities;
}

