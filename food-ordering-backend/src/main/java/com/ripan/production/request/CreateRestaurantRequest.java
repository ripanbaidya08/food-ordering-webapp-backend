package com.ripan.production.request;

import com.ripan.production.model.Address;
import com.ripan.production.model.ContactInformation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description ;

    private String cuisineType;

    @NotNull
    private Address address;

    private ContactInformation contactInformation;

    private String openingHours;

    private List<String> images;

}
