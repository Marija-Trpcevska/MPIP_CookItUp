package finki.ukim.mk.cookitup.domain.search.repository

import finki.ukim.mk.cookitup.domain.search.LocalRecipeApiDataSource
import finki.ukim.mk.cookitup.domain.search.RemoteRecipeDataSource
import finki.ukim.mk.cookitup.domain.search.model.RecipeApi
import finki.ukim.mk.cookitup.helpers.NetworkConnectivity

class RecipeApiRepository(
    private val remoteRecipeDataSource: RemoteRecipeDataSource,
    private val localRecipeApiDataSource: LocalRecipeApiDataSource,
    private val networkConnectivity: NetworkConnectivity
    ) {

    suspend fun searchForRecipes(query: String): List<RecipeApi>{
        return if(networkConnectivity.isNetworkAvailable){
            val recipes: List<RecipeApi> = remoteRecipeDataSource.search(query)
            localRecipeApiDataSource.clearCache()
            localRecipeApiDataSource.insertAllInCache(recipes)
            recipes
        } else {
            localRecipeApiDataSource.getCache()
        }
    }

    suspend fun listRecipesInCache(): List<RecipeApi>{
        return localRecipeApiDataSource.getCache()
    }

    suspend fun addRecipe(recipe: RecipeApi){
        return localRecipeApiDataSource.addRecipe(recipe.uri)
    }

    suspend fun deleteRecipe(recipe: RecipeApi){
        return localRecipeApiDataSource.deleteAddedRecipe(recipe.uri)
    }

    suspend fun listAddedRecipes(): List<RecipeApi>{
        return  localRecipeApiDataSource.getAddedRecipes()
    }

}