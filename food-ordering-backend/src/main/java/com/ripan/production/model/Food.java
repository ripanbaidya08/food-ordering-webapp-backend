package com.ripan.production.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private Long price;
    private boolean available;
    private boolean isVegetarian;
    private boolean isSeasonal;

    @ManyToOne(cascade = CascadeType.PERSIST) // Cascade if needed
    private Category foodCategory;

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    @ManyToOne(cascade = CascadeType.PERSIST) // Cascade if needed
    private Restaurant restaurant;

    @ManyToMany(cascade = CascadeType.PERSIST) // Cascade if needed
    private List<IngredientsItem> ingredients = new ArrayList<>();

    private Date creationAt;

    @PrePersist
    protected void onCreate() {
        creationAt = new Date();
    }
}
