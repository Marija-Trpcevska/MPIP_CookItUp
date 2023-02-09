package finki.ukim.mk.cookitup.domain.search.room

import androidx.room.*
import finki.ukim.mk.cookitup.domain.search.model.RecipeApi

@Dao
interface RecipeApiDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertInCache(recipe: RecipeApi)

    @Query("UPDATE api SET added=1 WHERE uri=:id")
    suspend fun addRecipe(id:String)

    @Query("DELETE FROM api WHERE added=0")
    suspend fun clearCache()

    @Query("DELETE FROM api WHERE uri=:id")
    suspend fun deleteAddedRecipe(id: String)

    @Query("SELECT * FROM api WHERE added=0")
    suspend fun getCache(): List<RecipeApi>

    @Query("SELECT * FROM api WHERE added=1")
    suspend fun getAddedRecipes(): List<RecipeApi>

}