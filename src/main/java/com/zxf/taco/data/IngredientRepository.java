package com.zxf.taco.data;

import com.zxf.taco.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
