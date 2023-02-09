package finki.ukim.mk.cookitup.domain.add.room

import finki.ukim.mk.cookitup.domain.add.LocalRecipeCameraDataSource
import finki.ukim.mk.cookitup.domain.add.model.RecipeCamera

class RoomRecipeCameraDataSource(private val recipeCameraDao: RecipeCameraDao): LocalRecipeCameraDataSource {
    override suspend fun addRecipe(recipe: RecipeCamera) {
        recipeCameraDao.addRecipe(recipe)
    }

    override suspend fun deleteAddedRecipe(id: Int) {
        recipeCameraDao.deleteAddedRecipe(id)
    }

    override suspend fun getAddedRecipes(): List<RecipeCamera> {
        return recipeCameraDao.getAddedRecipes()
    }
}