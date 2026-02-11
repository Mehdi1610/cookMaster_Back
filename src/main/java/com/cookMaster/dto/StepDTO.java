package com.cookMaster.dto;

import com.cookMaster.model.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepDTO {

    private Long id;

    private String description;

    private Integer stepNumber;

}
