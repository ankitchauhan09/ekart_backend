package com.example.ekart_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50, nullable = false)
    private String categoryName;
    @Column(length = 200, nullable = false)
    private String categoryDescription;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Product> product = new ArrayList<>();

}
