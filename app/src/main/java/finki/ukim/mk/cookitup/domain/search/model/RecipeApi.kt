package finki.ukim.mk.cookitup.domain.search.model

import androidx.room.Entity
import androidx.room.PrimaryKey

open class RecipeApiRoom {
    var added: Int = 0
}

@Entity(tableName = "api")
data class RecipeApi(
    @PrimaryKey val uri:String,
    val label:String,
    val image: String,
    val source: String,
    val url: String,
    val ingredientLines: List<String>,
    val mealType: List<String>) : RecipeApiRoom()