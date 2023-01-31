package finki.ukim.mk.cookitup.domain.search.repository

import finki.ukim.mk.cookitup.domain.search.RemoteRecipeDataSource
import finki.ukim.mk.cookitup.domain.search.model.RecipeApi

class RecipeApiRepository(private val remoteRecipeDataSource: RemoteRecipeDataSource) {

    suspend fun searchForRecipes(query: String): List<RecipeApi>{
        return remoteRecipeDataSource.search(query)
    }

}