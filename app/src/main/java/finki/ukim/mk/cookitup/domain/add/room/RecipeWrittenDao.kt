package finki.ukim.mk.cookitup.domain.add.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import finki.ukim.mk.cookitup.domain.add.model.RecipeWritten

@Dao
interface RecipeWrittenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: RecipeWritten)

    @Query("DELETE FROM written WHERE id=:id")
    suspend fun deleteAddedRecipe(id: Int)

    @Query("SELECT * FROM written")
    suspend fun getAddedRecipes(): List<RecipeWritten>

}