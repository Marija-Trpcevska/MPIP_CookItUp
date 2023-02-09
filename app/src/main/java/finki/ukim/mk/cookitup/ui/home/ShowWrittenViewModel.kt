package finki.ukim.mk.cookitup.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import finki.ukim.mk.cookitup.domain.add.model.RecipeWritten
import finki.ukim.mk.cookitup.domain.add.repository.RecipeWrittenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowWrittenViewModel(private val recipeWrittenRepository: RecipeWrittenRepository): ViewModel() {
    private val addedRecipesLiveData = MutableLiveData<List<RecipeWritten>>(mutableListOf())
    fun getAddedRecipesLiveData(): LiveData<List<RecipeWritten>> = addedRecipesLiveData

    fun addRecipe(recipe: RecipeWritten){
        viewModelScope.launch(Dispatchers.IO) {
            recipeWrittenRepository.addRecipe(recipe)
            addedRecipesLiveData.postValue(addedRecipesLiveData.value?.plus(recipe))
        }
    }

    fun deleteRecipe(recipe: RecipeWritten){
        viewModelScope.launch(Dispatchers.IO) {
            recipeWrittenRepository.deleteRecipe(recipe)
            addedRecipesLiveData.postValue(addedRecipesLiveData.value?.minus(recipe))
        }
    }

    fun listAddedRecipes(){
        viewModelScope.launch(Dispatchers.IO) {
            val recipes = recipeWrittenRepository.listAddedRecipes()
            addedRecipesLiveData.postValue(recipes)
        }
    }
}