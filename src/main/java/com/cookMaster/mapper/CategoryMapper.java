package com.cookMaster.mapper;

import com.cookMaster.dto.CategoryDTO;
import com.cookMaster.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryDTO categoryDTO);
    CategoryDTO toDto(Category category);
}