package com.example.Backend.dto;

//import com.example.Backend.entity.User;
//import jakarta.validation.constraints.NotBlank;
import com.example.Backend.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;



public class ServicesDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Service center id must be provided")
    private User serviceCenterId;

    @NotBlank(message = "Service name cannot be blank")
    private String serviceName;

   @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotBlank(message = "Delivery time cannot be blank")
    @Pattern(regexp = "^\\d+ (hours|days)$", message = "Delivery time must be in 'X hours' or 'X days' format")
    private String deliveryTime;


    @NotNull(message = "price not null")
    @Positive(message = "Price must be a positive value")
    private String minPrice;

//   @NotNull(message = "Service center ID cannot be null")
//    private User serviceCenterId;


    public @NotNull(message = "Service center id must be provided") User getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(@NotNull(message = "Service center id must be provided") User serviceCenterId) {
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
}
