package com.cookMaster.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotBlank(message = "Ajouter un nom")
    private String title;

    @Column(name = "preparationtime")
    @NotBlank(message = "Ajouter une durée")
    private Integer preparationTime;

    @Column(name = "difficulty")
    @NotBlank(message = "Ajouter une difficulté")
    private String difficulty;

    @Column(name = "imageurl")
    private String imageUrl;



    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;


    @ManyToOne
    @JoinColumn(name = "categoryid", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "recipe")
    private Set<Favorite> favorites = new LinkedHashSet<>();
}
