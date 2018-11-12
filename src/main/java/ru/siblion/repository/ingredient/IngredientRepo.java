package ru.siblion.repository.ingredient;

import ru.siblion.tacos.Ingredient;

public interface IngredientRepo {
    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
