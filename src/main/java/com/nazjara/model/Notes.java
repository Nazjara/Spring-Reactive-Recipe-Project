package com.nazjara.model;

import lombok.Data;

@Data
public class Notes {

    private String id;
    private String recipeNotes;
    private Recipe recipe;

    public Notes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }

    public Notes() {
    }
}