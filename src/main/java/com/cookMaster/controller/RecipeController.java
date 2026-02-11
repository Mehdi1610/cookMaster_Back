package com.cookMaster.controller;

import com.cookMaster.dto.RecipeDTO;
import com.cookMaster.exceptions.TechnicalException;
import com.cookMaster.service.recipeService.RecipeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
            @RequestPart("recipe") @Valid RecipeDTO recipeDTO,
            @RequestPart("file") MultipartFile file) throws IOException, TechnicalException {
        log.info("POST recipe : {}", recipeDTO.getTitle());
        if (file.isEmpty()){
            throw  new TechnicalException("File is empty ! Please send another file!");
        }
        return ResponseEntity.ok(recipeService.createRecipe(recipeDTO, file));
    }

    @GetMapping("recipes/{id}")
    public ResponseEntity<List<RecipeDTO>> getRecipes(@PathVariable Long id) {
        List<RecipeDTO> recipes = recipeService.getAllRecipesByUser(id);
        log.info("GET recipes : {}", recipes);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("recipe/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) {
        RecipeDTO recipe = recipeService.getRecipe(id);
        log.info("GET recipe : {}", recipe);
        return ResponseEntity.ok(recipe);
    }

    @PatchMapping("recipe/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@PathVariable Long id, @RequestPart(required = false) RecipeDTO recipeDTO, @RequestPart(required = false) MultipartFile file) throws IOException {
        return ResponseEntity.ok(recipeService.updateByRecipeId(id, recipeDTO, file));
    }

    @DeleteMapping("recipe/{id}")
    public ResponseEntity<String> deleteRecipeById(@PathVariable Long id) throws IOException {
        recipeService.deleteRecipeById(id);
        return ResponseEntity.ok("OK");
    }


}
