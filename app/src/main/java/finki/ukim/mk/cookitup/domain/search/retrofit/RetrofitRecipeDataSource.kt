package finki.ukim.mk.cookitup.domain.search.retrofit

import finki.ukim.mk.cookitup.domain.search.RemoteRecipeDataSource
import finki.ukim.mk.cookitup.domain.search.model.RecipeApi

class RetrofitRecipeDataSource(private val recipeDbApi: RecipeDbApi) : RemoteRecipeDataSource{

    override suspend fun search(query: String): List<RecipeApi> {
        val recipeResponse = recipeDbApi.search(query)
        val responseBody = recipeResponse.body()
        if (recipeResponse.isSuccessful && responseBody!=null) {
            return responseBody.hits.map { it.recipe }
        }
        throw Exception(recipeResponse.message())
    }
}