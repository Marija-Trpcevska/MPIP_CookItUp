package finki.ukim.mk.cookitup.domain.add.model

import android.graphics.Bitmap


data class RecipeWritten(val label:String, val image: Bitmap, val ingredientLines: ArrayList<String>, val instructions: String, val mealType: ArrayList<String>)