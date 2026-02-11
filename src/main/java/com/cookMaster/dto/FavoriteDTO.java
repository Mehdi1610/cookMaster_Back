package com.cookMaster.dto;

import com.cookMaster.model.Recipe;
import com.cookMaster.model.User;
import com.cookMaster.model.UserFavoriteId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDTO {


    private UserFavoriteId id;

    private User user;

    private Recipe recipe;
}
