package finki.ukim.mk.cookitup.domain.add

import finki.ukim.mk.cookitup.domain.add.model.RecipeWritten


interface LocalRecipeWrittenDataSource {

    suspend fun addRecipe(recipe: RecipeWritten)

    suspend fun deleteAddedRecipe(id: Int)

    suspend fun getAddedRecipes(): List<RecipeWritten>
}