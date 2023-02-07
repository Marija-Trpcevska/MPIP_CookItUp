package finki.ukim.mk.cookitup.domain.search.room

import finki.ukim.mk.cookitup.domain.search.LocalRecipeApiDataSource
import finki.ukim.mk.cookitup.domain.search.model.RecipeApi

class RoomRecipeApiDataSource(private val recipeApiDao: RecipeApiDao): LocalRecipeApiDataSource {
    override suspend fun insertInCache(recipe: RecipeApi) {
        recipeApiDao.insertInCache(recipe)
    }

    override suspend fun insertAllInCache(recipes: List<RecipeApi>) {
        for(recipe in recipes){
            recipeApiDao.insertInCache(recipe)
        }

    }

    override suspend fun addRecipe(id: String) {
        recipeApiDao.addRecipe(id)
    }

    override suspend fun clearCache() {
        recipeApiDao.clearCache()
    }

    override suspend fun deleteAddedRecipe(id: String) {
        recipeApiDao.deleteAddedRecipe(id)
    }

    override suspend fun getCache(): List<RecipeApi> {
        return recipeApiDao.getCache()
    }

    override suspend fun getAddedRecipes(): List<RecipeApi> {
        return recipeApiDao.getAddedRecipes()
    }

    override suspend fun getAddedRecipe(id: String): RecipeApi {
        return recipeApiDao.getAddedRecipe(id)
    }

}


