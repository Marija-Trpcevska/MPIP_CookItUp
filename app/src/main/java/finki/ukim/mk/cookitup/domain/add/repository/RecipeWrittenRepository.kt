package finki.ukim.mk.cookitup.domain.add.repository

import finki.ukim.mk.cookitup.domain.add.LocalRecipeWrittenDataSource
import finki.ukim.mk.cookitup.domain.add.model.RecipeWritten

class RecipeWrittenRepository(private val localRecipeWrittenDataSource: LocalRecipeWrittenDataSource) {
    suspend fun addRecipe(recipe: RecipeWritten){
        return localRecipeWrittenDataSource.addRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: RecipeWritten){
        return localRecipeWrittenDataSource.deleteAddedRecipe(recipe.id)
    }

    suspend fun listAddedRecipes(): List<RecipeWritten>{
        return  localRecipeWrittenDataSource.getAddedRecipes()
    }
}