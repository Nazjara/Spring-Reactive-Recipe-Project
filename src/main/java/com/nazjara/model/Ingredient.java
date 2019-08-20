package com.nazjara.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Ingredient {

    private String id = UUID.randomUUID().toString();
    private String description;
    private String amount;
    private Recipe recipe;
    private UnitOfMeasure unitOfMeasure;

    public Ingredient() {
    }

    public Ingredient(String description, String amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }
}