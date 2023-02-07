package finki.ukim.mk.cookitup.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import finki.ukim.mk.cookitup.domain.search.model.RecipeApi
import finki.ukim.mk.cookitup.domain.search.repository.RecipeApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowApiViewModel(private val recipeApiRepository: RecipeApiRepository): ViewModel() {
    private val addedRecipesLiveData = MutableLiveData<List<RecipeApi>>()
    fun getAddedRecipesLiveData(): LiveData<List<RecipeApi>> = addedRecipesLiveData

    fun addRecipe(recipe: RecipeApi){
        viewModelScope.launch(Dispatchers.IO) {
            recipeApiRepository.addRecipe(recipe)
            addedRecipesLiveData.postValue(addedRecipesLiveData.value?.plus(recipe))
            //pomalku thread overhead od addRecipe followed by listAddedRecipes()?
        }
    }

    fun deleteRecipe(recipe: RecipeApi){
        viewModelScope.launch(Dispatchers.IO) {
            recipeApiRepository.deleteRecipe(recipe)
            addedRecipesLiveData.postValue(addedRecipesLiveData.value?.minus(recipe))
            //pomalku thread overhead od deleteRecipe followed by listAddedRecipes()?
        }
    }

    fun listAddedRecipes(){
        viewModelScope.launch(Dispatchers.IO) {
            val recipes = recipeApiRepository.listAddedRecipes()
            addedRecipesLiveData.postValue(recipes)
        }
    }
}