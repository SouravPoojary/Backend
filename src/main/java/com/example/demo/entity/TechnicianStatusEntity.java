package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "tech_status")
public class TechnicianStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String status;
//    private Date updateDate;

    @ManyToOne
    private TechnicianDetailsEntity technicianDetailsEntity;

//    @ManyToOne
//    @JoinColumn(name = "service_appointment_id", nullable = false)
//    private ServiceAppointmentEntity serviceAppointmentEntity;
}
