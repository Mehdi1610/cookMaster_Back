package com.cookMaster.dto;

import java.util.List;

public record RecipePageResponse(List<RecipeDTO> recipeDTOS,
                                 Integer pageNumber,
                                 Integer pageSize,
                                 long totalElements,
                                 int totalPages,
                                 boolean isLast) {
    
}
