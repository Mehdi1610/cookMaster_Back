package com.cookMaster.repository;

import com.cookMaster.model.Favorite;
import com.cookMaster.model.UserFavoriteId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, UserFavoriteId> {

    List<Favorite> findByUserId(Long userId);
    boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);
    @Transactional
    void deleteByUserIdAndRecipeId(Long userId, Long recipeId);
}