package com.cookMaster.service.favoriteService;

import com.cookMaster.dto.RecipeDTO;
import com.cookMaster.model.Favorite;

import java.util.List;

public interface FavoriteService {

    void addFavorite(Long userId, Long recipeId);
    void removeFavorite(Long userId, Long recipeId);
    boolean isFavorite(Long userId, Long recipeId);
    List<RecipeDTO> getFavorites(Long userId);
}
