package com.cookMaster.mapper;

import com.cookMaster.dto.CategoryDTO;
import com.cookMaster.dto.IngredientDTO;
import com.cookMaster.dto.RecipeDTO;
import com.cookMaster.dto.StepDTO;
import com.cookMaster.model.Category;
import com.cookMaster.model.Ingredient;
import com.cookMaster.model.Recipe;
import com.cookMaster.model.Step;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "favorites", ignore = true)
    Recipe toEntity(RecipeDTO recipeDTO);

    Step stepToEntity(StepDTO dto);

    Ingredient ingredientToEntity(IngredientDTO dto);

    RecipeDTO toDto(Recipe recipe);

    List<StepDTO> stepsToDtos(List<Step> steps);
    List<IngredientDTO> ingredientsToDtos(List<Ingredient> ingredients);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRecipeFromDto(RecipeDTO recipeDTO, @MappingTarget Recipe recipe);
}
