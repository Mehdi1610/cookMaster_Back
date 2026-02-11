package com.cookMaster.controller;

import com.cookMaster.dto.CategoryDTO;
import com.cookMaster.service.categoryService.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        log.info("GET Categories : {}" , categories );
        return ResponseEntity.ok(categories);
    }

    @PostMapping("category")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("POST Category : {}", categoryDTO.getName());
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }
}