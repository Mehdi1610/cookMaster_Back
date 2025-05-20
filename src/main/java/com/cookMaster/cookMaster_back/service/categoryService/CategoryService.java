package com.cookMaster.cookMaster_back.service.categoryService;

import com.cookMaster.cookMaster_back.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getAllCategories();
}
