package finki.ukim.mk.cookitup.domain.search.model

class RecipeApi(val uri:String, val label:String, val image: String, val source: String, val url: String, val ingredientLines: List<String>, val mealType: List<String>)