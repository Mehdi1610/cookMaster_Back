package com.cookMaster.dto;

import com.cookMaster.model.Category;
import com.cookMaster.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
public class RecipeDTO {

    private Long id;

    private String title;

    private Integer preparationTime;

    private String difficulty;

    private String imageUrl;

    private Long userId;

    private Long categoryId;

    private List<StepDTO> steps;
    private List<IngredientDTO> ingredients;

}
