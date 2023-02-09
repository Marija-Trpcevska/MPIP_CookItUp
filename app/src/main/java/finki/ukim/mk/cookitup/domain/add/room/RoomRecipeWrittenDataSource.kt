package finki.ukim.mk.cookitup.domain.add.room

import finki.ukim.mk.cookitup.domain.add.LocalRecipeWrittenDataSource
import finki.ukim.mk.cookitup.domain.add.model.RecipeWritten

class RoomRecipeWrittenDataSource(private val recipeWrittenDao: RecipeWrittenDao): LocalRecipeWrittenDataSource{
    override suspend fun addRecipe(recipe: RecipeWritten) {
        recipeWrittenDao.addRecipe(recipe)
    }

    override suspend fun deleteAddedRecipe(id: Int) {
        recipeWrittenDao.deleteAddedRecipe(id)
    }

    override suspend fun getAddedRecipes(): List<RecipeWritten> {
        return recipeWrittenDao.getAddedRecipes()
    }
}