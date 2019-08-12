package com.nazjara.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private String id;
    private String recipeId;
    private String description;
    private String amount;
    private UnitOfMeasureCommand unitOfMeasureCommand;
}