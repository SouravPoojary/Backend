package com.example.Backend.entity;

import jakarta.persistence.*;




import java.util.List;

@Entity

//@NoArgsConstructor
//@AllArgsConstructor
@Table(name = "services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "service_center_id")
    private User serviceCenterId;
    private String serviceName;
    private String description;
    private String category;
    private String deliveryTime;
    private String minPrice;
    @OneToMany(mappedBy = "serviceId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    public User getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(User serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }
    //    @ManyToOne
//    @JoinColumn(name = "service_center_id")
//    private User serviceCenterId;  // Link to Service Center
}
