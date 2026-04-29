package com.cookMaster.dto;

import java.util.List;

public record RecipePageResponse(List<RecipeDTO> recipeDTOS,
                                 Integer pageNumber,
                                 Integer pageSize,
                                 int totalElements,
                                 int totalPages,
                                 boolean isLast) {
    
}
