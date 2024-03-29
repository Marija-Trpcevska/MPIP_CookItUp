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
    private val addedRecipesLiveData = MutableLiveData<List<RecipeApi>>(mutableListOf())
    fun getAddedRecipesLiveData(): LiveData<List<RecipeApi>> = addedRecipesLiveData

    fun addRecipe(recipe: RecipeApi){
        viewModelScope.launch(Dispatchers.IO) {
            recipeApiRepository.addRecipe(recipe)
            addedRecipesLiveData.postValue(addedRecipesLiveData.value?.plus(recipe))
        }
    }

    fun deleteRecipe(recipe: RecipeApi){
        viewModelScope.launch(Dispatchers.IO) {
            recipeApiRepository.deleteRecipe(recipe)
            addedRecipesLiveData.postValue(addedRecipesLiveData.value?.minus(recipe))
        }
    }

    fun listAddedRecipes(){
        viewModelScope.launch(Dispatchers.IO) {
            val recipes = recipeApiRepository.listAddedRecipes()
            addedRecipesLiveData.postValue(recipes)
        }
    }
}