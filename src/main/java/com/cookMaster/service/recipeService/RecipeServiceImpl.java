package com.cookMaster.service.recipeService;

import com.cookMaster.dto.RecipeDTO;
import com.cookMaster.dto.RecipePageResponse;
import com.cookMaster.exceptions.FileExistsException;
import com.cookMaster.exceptions.NotFoundException;
import com.cookMaster.mapper.RecipeMapper;
import com.cookMaster.model.*;
import com.cookMaster.repository.CategoryRepository;
import com.cookMaster.repository.RecipeRepository;
import com.cookMaster.repository.UserRepository;
import com.cookMaster.service.fileService.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final FileService fileService;

    @Value("${project.image}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, FileService fileService,
                             UserRepository userRepository,
                             CategoryRepository categoryRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.fileService = fileService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public RecipeDTO createRecipe(RecipeDTO recipeDTO, MultipartFile file) throws IOException {

        //Recuperer l'utilisateur
        User user = userRepository.findById(recipeDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable"));

        //Recuperer la catégorie
        Category category = categoryRepository.findById(recipeDTO.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Catégorie introuvable"));

        //Preparer l'image url
        if(Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))){
            throw new FileExistsException("le fichier existe déjà! Veuillez entrer un nouveau fichier");
        }
        String uploadedFileName = fileService.uploadFile(path,file);
        String imageUrl = baseUrl + "/api/v1/file/" + uploadedFileName;

        //créer la recette
        Recipe recipe = recipeMapper.toEntity(recipeDTO);
        recipe.setImageUrl(imageUrl);
        recipe.setUser(user);
        recipe.setCategory(category);

        //Ajouter les étapes
        if(recipeDTO.getSteps() != null){
            recipe.setSteps(recipeDTO.getSteps().stream().map(stepDTO ->{
                       Step step = recipeMapper.stepToEntity(stepDTO);
                       step.setRecipe(recipe);
                       return step;
                    }).collect(Collectors.toList()));
        }
        //Ajouter les ingredients
        if(recipeDTO.getIngredients() != null){
            recipe.setIngredients(recipeDTO.getIngredients().stream().map(ingredientDTO -> {
                Ingredient ingredient = recipeMapper.ingredientToEntity(ingredientDTO);
                ingredient.setRecipe(recipe);
                return ingredient;
            }).collect(Collectors.toList()));
        }
        //Sauvegarder La recette
        Recipe saved = recipeRepository.save(recipe);

        RecipeDTO result = recipeMapper.toDto(saved);
        result.setUserId(saved.getUser().getId());
        result.setCategoryId(saved.getCategory().getId());
        return result;
    }

    @Override
    public RecipeDTO getRecipe(Long recipeId) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recette non trouvée"));
        RecipeDTO result = recipeMapper.toDto(recipe);
        result.setUserId(recipe.getUser().getId());
        result.setCategoryId(recipe.getCategory().getId());
        return result;
    }

    @Override
    public List<RecipeDTO> getAllRecipesByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé avec l'id : " + userId));

        List<Recipe> recipes = recipeRepository.findByUserId(userId);
        return recipes.stream()
                .map(recipe -> {
                    RecipeDTO recipeDTO = recipeMapper.toDto(recipe);
                    recipeDTO.setUserId(recipe.getUser().getId());
                    recipeDTO.setCategoryId(recipe.getCategory().getId());
                    return recipeDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDTO updateByRecipeId(Long id, RecipeDTO recipeDTO, MultipartFile file) throws IOException {

        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recette non trouvé avec l'id : "+ id));


        recipeMapper.updateRecipeFromDto(recipeDTO,recipe);
        if (recipeDTO != null){
            if(recipeDTO.getCategoryId() != null){
                Category category = categoryRepository.findById(recipeDTO.getCategoryId())
                        .orElseThrow(() -> new NotFoundException("Catégorie introuvable"));
                recipe.setCategory(category);
                recipeRepository.save(recipe);
            }
        }

        String fileName = recipe.getImageUrl().substring(recipe.getImageUrl().lastIndexOf("/") + 1);
        if (file != null){
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
             fileName = fileService.uploadFile(path,file);
            String imageUrl = baseUrl + "/api/v1/file/" + fileName;
            recipe.setImageUrl(imageUrl);
        }

        RecipeDTO result = recipeMapper.toDto(recipeRepository.save(recipe));
        result.setUserId(recipe.getUser().getId());
        result.setCategoryId(recipe.getCategory().getId());
        return result;
    }

    @Override
    public void deleteRecipeById(Long recipeId) throws IOException {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new NotFoundException("Recette non existante"));

        String fileName = recipe.getImageUrl().substring(recipe.getImageUrl().lastIndexOf("/") + 1);
        Files.deleteIfExists(Paths.get(path + File.separator + fileName));
        recipeRepository.delete(recipe);
    }

    @Override
    public RecipePageResponse getAllRecipeWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        //A MODIFIER SUR UN GETALLRECIPEBYID

        Page<Recipe> recipePages = recipeRepository.findAll(pageable);
        List<RecipeDTO> recipes = recipePages.getContent().stream().map(recipeMapper::toDto).toList();

        return new RecipePageResponse(recipes, pageNumber, pageSize, recipePages.getNumberOfElements(), recipePages.getTotalPages(), recipePages.isLast());
    }

    @Override
    public RecipePageResponse getAllRecipeWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {

        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                                                                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize, sort);
        //A MODIFIER SUR UN GETALLRECIPEBYID

        Page<Recipe> recipePages = recipeRepository.findAll(pageable);
        List<RecipeDTO> recipes = recipePages.getContent().stream().map(recipeMapper::toDto).toList();

        return new RecipePageResponse(recipes, pageNumber, pageSize, recipePages.getNumberOfElements(), recipePages.getTotalPages(), recipePages.isLast());
    }

}