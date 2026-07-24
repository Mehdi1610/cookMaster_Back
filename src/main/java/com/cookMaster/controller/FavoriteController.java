package com.cookMaster.controller;
import com.cookMaster.dto.RecipeDTO;
import com.cookMaster.model.User;
import com.cookMaster.service.favoriteService.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getFavorites(Authentication authentication) {
        Long userId = extractUserId(authentication);
        return ResponseEntity.ok(favoriteService.getFavorites(userId));
    }

    @PostMapping("/{recipeId}")
    public ResponseEntity<Void> addFavorite(Authentication authentication, @PathVariable Long recipeId) {
        Long userId = extractUserId(authentication);
        favoriteService.addFavorite(userId, recipeId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> removeFavorite(Authentication authentication, @PathVariable Long recipeId) {
        Long userId = extractUserId(authentication);
        favoriteService.removeFavorite(userId, recipeId);
        return ResponseEntity.noContent().build();
    }

    private Long extractUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}