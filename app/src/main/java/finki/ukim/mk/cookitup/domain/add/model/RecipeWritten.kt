package finki.ukim.mk.cookitup.domain.add.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "written")
data class RecipeWritten(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val label:String,
    val image: String,
    val ingredientLines: ArrayList<String>,
    val instructions: String,
    val mealType: ArrayList<String>)