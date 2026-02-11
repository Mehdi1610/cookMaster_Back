package com.cookMaster.dto;

import com.cookMaster.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDTO {

    private Long id;

    private String name;

    private String quantity;

}
