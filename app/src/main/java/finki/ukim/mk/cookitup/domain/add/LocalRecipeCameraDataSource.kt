package finki.ukim.mk.cookitup.domain.add

import finki.ukim.mk.cookitup.domain.add.model.RecipeCamera


interface LocalRecipeCameraDataSource {

    suspend fun addRecipe(recipe: RecipeCamera)

    suspend fun deleteAddedRecipe(id: Int)

    suspend fun getAddedRecipes(): List<RecipeCamera>
}