package com.cookMaster.service.favoriteService;

import com.cookMaster.dto.RecipeDTO;
import com.cookMaster.mapper.RecipeMapper;
import com.cookMaster.model.Favorite;
import com.cookMaster.model.Recipe;
import com.cookMaster.model.User;
import com.cookMaster.model.UserFavoriteId;
import com.cookMaster.repository.FavoriteRepository;
import com.cookMaster.repository.RecipeRepository;
import com.cookMaster.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService{


    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;


    public FavoriteServiceImpl(FavoriteRepository favoriteRepository, UserRepository userRepository, RecipeRepository recipeRepository, RecipeMapper recipeMapper){
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;
    }
    @Override
    public void addFavorite(Long userId, Long recipeId) {
        if( favoriteRepository.existsByUserIdAndRecipeId(userId,recipeId)){
            return;
        }
        User user = userRepository.findById(userId)
                .orElseThrow();

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow();

        Favorite favorite = new Favorite(
                new UserFavoriteId(userId,recipeId),
                user,
                recipe
        );
        favoriteRepository.save(favorite);
    }

    @Override
    public List<RecipeDTO> getFavorites(Long userId) {

        return favoriteRepository.findByUserId(userId).stream()
                .map(favorite -> recipeMapper.toDto(favorite.getRecipe()))
                .collect(Collectors.toList());
    }

    @Override
    public void removeFavorite(Long userId, Long recipeId) {
        favoriteRepository.deleteByUserIdAndRecipeId(userId,recipeId);
    }

    @Override
    public boolean isFavorite(Long userId, Long recipeId) {
        return favoriteRepository.existsByUserIdAndRecipeId(userId, recipeId);
    }


}
