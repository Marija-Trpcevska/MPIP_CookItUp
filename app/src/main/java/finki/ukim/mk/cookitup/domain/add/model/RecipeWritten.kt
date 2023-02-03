package finki.ukim.mk.cookitup.domain.add.model

import androidx.compose.ui.graphics.ImageBitmap

data class RecipeWritten(val label:String, val image: ImageBitmap, val ingredientLines: ArrayList<String>, val instructions: String, val mealType: ArrayList<String>)