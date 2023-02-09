package finki.ukim.mk.cookitup.domain.add.repository

import finki.ukim.mk.cookitup.domain.add.LocalRecipeCameraDataSource
import finki.ukim.mk.cookitup.domain.add.model.RecipeCamera

class RecipeCameraRepository(private val localRecipeCameraDataSource: LocalRecipeCameraDataSource){

    suspend fun addRecipe(recipe: RecipeCamera){
        return localRecipeCameraDataSource.addRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: RecipeCamera){
        return localRecipeCameraDataSource.deleteAddedRecipe(recipe.id)
    }

    suspend fun listAddedRecipes(): List<RecipeCamera>{
        return  localRecipeCameraDataSource.getAddedRecipes()
    }
}