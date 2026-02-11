package com.cookMaster.repository;

import com.cookMaster.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {

    List<Recipe> findByUserId(Long userId);
}
