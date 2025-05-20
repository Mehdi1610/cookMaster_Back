package com.cookMaster.cookMaster_back.mapper;

import com.cookMaster.cookMaster_back.dto.CategoryDTO;
import com.cookMaster.cookMaster_back.model.Category;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryDTO categoryDTO);
    CategoryDTO toDto(Category category);
    List<CategoryDTO> toDto(List<Category> categories);
    List<Category> toEntity(List<CategoryDTO> categoryDTOS);

}
