package finki.ukim.mk.cookitup.domain.search.retrofit

import finki.ukim.mk.cookitup.domain.search.model.RecipeApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeDbApi {

    @GET("api/recipes/v2?type=public&random=true")
    suspend fun search(@Query("q") query: String): Response<RecipeApiResponse>
}