package com.cookMaster.cookMaster_back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    @JsonProperty
    private long id;

    @JsonProperty
    private String name;


    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}