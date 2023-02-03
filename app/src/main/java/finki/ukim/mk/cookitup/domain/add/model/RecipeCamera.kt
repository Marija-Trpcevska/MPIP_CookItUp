package finki.ukim.mk.cookitup.domain.add.model

import androidx.compose.ui.graphics.ImageBitmap

data class RecipeCamera(val label:String, val image: ImageBitmap, val notes: String, val mealType: List<String>)