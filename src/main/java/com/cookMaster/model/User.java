package com.cookMaster.model;

import com.cookMaster.dto.RecipeDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Person")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Recipe> recipes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Favorite> favorites = new LinkedHashSet<>();

}

