package finki.ukim.mk.cookitup.domain.add.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import finki.ukim.mk.cookitup.domain.add.model.RecipeCamera

@Dao
interface RecipeCameraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: RecipeCamera)

    @Query("DELETE FROM camera WHERE id=:id")
    suspend fun deleteAddedRecipe(id: Int)

    @Query("SELECT * FROM camera")
    suspend fun getAddedRecipes(): List<RecipeCamera>
}