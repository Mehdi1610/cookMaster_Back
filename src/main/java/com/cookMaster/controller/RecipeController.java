package com.cookMaster.controller;

import com.cookMaster.dto.RecipeDTO;
import com.cookMaster.dto.RecipePageResponse;
import com.cookMaster.exceptions.TechnicalException;
import com.cookMaster.model.User;
import com.cookMaster.service.recipeService.RecipeService;
import com.cookMaster.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @PostMapping(value = "recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipeDTO> createRecipe(
            @RequestPart("recipeDTO") @Valid RecipeDTO recipeDTO,
            @RequestPart(value ="file", required = false) Optional<MultipartFile> file,
            Authentication authentication) throws IOException{

        log.info("POST recipe : {}", recipeDTO.getTitle());
        log.info("categoryId reçu : {}", recipeDTO.getCategoryId());
        User currentUser = (User) authentication.getPrincipal();
        recipeDTO.setUserId(currentUser.getId());
        MultipartFile imageFile = file.filter(f -> !f.isEmpty()).orElse(null);
        return ResponseEntity.ok(recipeService.createRecipe(recipeDTO, imageFile));



    }

    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeDTO>> getRecipes(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<RecipeDTO> recipes = recipeService.getAllRecipesByUser(user.getId());
        return ResponseEntity.ok(recipes);
    }
    //aussi a restreindre selon l'id user connecté
    @GetMapping("/allRecipesPage")
    public ResponseEntity<RecipePageResponse> getRecipesWithPagination(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                       @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
        RecipePageResponse recipePages = recipeService.getAllRecipeWithPagination(pageNumber,pageSize);
        log.info("GET recipesPage : {}", recipePages);
        return ResponseEntity.ok(recipePages);
    }
    //aussi a restreindre selon l'id user connecté
    @GetMapping("/allRecipesPageSort")
    public ResponseEntity<RecipePageResponse> getRecipesWithPaginationAndSorting(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                                 @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                                 @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                                                 @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String dir) {
        RecipePageResponse recipePagesWithSorting = recipeService.getAllRecipeWithPaginationAndSorting(pageNumber,pageSize,sortBy,dir);
        log.info("GET recipesPageWithSorting : {}", recipePagesWithSorting);
        return ResponseEntity.ok(recipePagesWithSorting);
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        RecipeDTO recipe = recipeService.getRecipe(id);
        log.info("GET recipe : {}", recipe);
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/recipe/{id}/edit")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long id, @RequestPart(required = false) RecipeDTO recipeDTO, @RequestPart(required = false) Optional<MultipartFile> file,  Authentication authentication) throws IOException {
        User currentUser = (User) authentication.getPrincipal();
        MultipartFile imageFile = file.filter(f -> !f.isEmpty()).orElse(null);
        return ResponseEntity.ok(recipeService.updateRecipe(id, recipeDTO, imageFile, currentUser.getId()));
    }
    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Long id) throws IOException {
        recipeService.deleteRecipeById(id);
        return ResponseEntity.noContent().build();
    }
}
