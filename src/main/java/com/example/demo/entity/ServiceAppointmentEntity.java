package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "service appointment")
public class ServiceAppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String appDate;

    @ManyToOne
    @JoinColumn(name = "technician_id", referencedColumnName = "id")
    private TechnicianDetailsEntity technicianDetailsEntity;

    @ManyToOne
    @JoinColumn(name = "service_type_id", referencedColumnName = "id")
    private ServiceTypeEntity serviceTypeEntity;

//    @OneToMany(mappedBy = "serviceAppointmentEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<HistoryEntity> historyRecords;



}


//    @ManyToOne
//    @JoinColumn(name ="service_manager_id", nullable = false)
//    private ServiceManagerEntity serviceManagerEntity;

//    @ManyToOne
//    @JoinColumn(name ="vehicle_id", referencedColumnName = "id")
//    private VehicleDetailsEntity vehicleDetailsEntity;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "service_appointment_id")
//    private List<TechnicianStatusEntity> technicianStatusEntities;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "service_appointment_id")
//    private List<NotificationEntity> notificationEntities;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "service_appointment_id")
//    private List<FeedbackEntity>feedbackEntities;


