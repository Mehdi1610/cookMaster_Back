package com.cookMaster.service.recipeService;

import com.cookMaster.dto.RecipeDTO;
import com.cookMaster.dto.RecipePageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecipeService {

    RecipeDTO createRecipe(RecipeDTO recipeDTO, MultipartFile file) throws IOException;


    RecipeDTO getRecipe(Long recipeId);

    List<RecipeDTO> getAllRecipesByUser(Long userId);


    RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO, MultipartFile file, Long currentUserId) throws IOException;

    void deleteRecipeById(Long recipeId) throws IOException;

    RecipePageResponse getAllRecipeWithPagination(Integer pageNumber, Integer pageSize);

    RecipePageResponse getAllRecipeWithPaginationAndSorting(Integer pageNumber, Integer pageSize,
                                                            String sortBy, String dir);


}
