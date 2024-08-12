package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Feedback")
@Data
public class FeedbackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String comment;
    private String rating;

    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceTypeEntity serviceTypeEntity ;

    @ManyToOne
    @JoinColumn(name = "technician_id", nullable = false)
    private TechnicianDetailsEntity technicianDetailsEntity;
}
