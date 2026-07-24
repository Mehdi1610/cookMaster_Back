package com.cookMaster.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserResponseDTO {


    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;


    @JsonProperty
    private String email;
}
