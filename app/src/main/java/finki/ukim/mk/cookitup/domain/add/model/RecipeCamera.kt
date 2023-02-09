package finki.ukim.mk.cookitup.domain.add.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "camera")
data class RecipeCamera(
    @PrimaryKey(autoGenerate = true) var id: Int,
    val label:String,
    val image: String,
    val notes: String,
    val mealType: List<String>)