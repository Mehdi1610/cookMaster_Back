package com.cookMaster.dto;

import com.cookMaster.utils.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientDTO {

    private Long id;

    private String name;

    private Double quantity;

    private Unit unit;

}
