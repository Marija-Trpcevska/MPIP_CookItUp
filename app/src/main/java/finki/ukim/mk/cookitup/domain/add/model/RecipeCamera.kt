package finki.ukim.mk.cookitup.domain.add.model

import android.graphics.Bitmap

data class RecipeCamera(val label:String, val image: Bitmap, val notes: String, val mealType: List<String>)