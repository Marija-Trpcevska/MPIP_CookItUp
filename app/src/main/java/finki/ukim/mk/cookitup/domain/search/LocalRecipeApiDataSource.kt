package finki.ukim.mk.cookitup.domain.search

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import finki.ukim.mk.cookitup.domain.search.model.RecipeApi

interface LocalRecipeApiDataSource {

    suspend fun insertInCache(recipe: RecipeApi)

    suspend fun insertAllInCache(recipes: List<RecipeApi>)

    suspend fun addRecipe(id:String)

    suspend fun clearCache()

    suspend fun deleteAddedRecipe(id: String)

    suspend fun getCache(): List<RecipeApi>

    suspend fun getAddedRecipes(): List<RecipeApi>

    suspend fun getAddedRecipe(id: String): RecipeApi
}