package com.cookMaster.cookMaster_back.service.categoryService;

import com.cookMaster.cookMaster_back.dto.CategoryDTO;
import com.cookMaster.cookMaster_back.mapper.CategoryMapper;
import com.cookMaster.cookMaster_back.model.Category;
import com.cookMaster.cookMaster_back.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper){
        this.categoryRepository=categoryRepository;
        this.categoryMapper=categoryMapper;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        return categoryMapper.toDto(categoryRepository.save(category));
    }


    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toDto(categories);

    }

}
