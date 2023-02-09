package finki.ukim.mk.cookitup.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import finki.ukim.mk.cookitup.domain.add.model.RecipeCamera
import finki.ukim.mk.cookitup.domain.add.repository.RecipeCameraRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowCameraViewModel(private val recipeCameraRepository: RecipeCameraRepository): ViewModel() {
    private val addedRecipesLiveData = MutableLiveData<List<RecipeCamera>>(mutableListOf())
    fun getAddedRecipesLiveData(): LiveData<List<RecipeCamera>> = addedRecipesLiveData

    fun addRecipe(recipe: RecipeCamera){
        viewModelScope.launch(Dispatchers.IO) {
            recipeCameraRepository.addRecipe(recipe)
            addedRecipesLiveData.postValue(addedRecipesLiveData.value?.plus(recipe))
        }
    }

    fun deleteRecipe(recipe: RecipeCamera){
        viewModelScope.launch(Dispatchers.IO) {
            recipeCameraRepository.deleteRecipe(recipe)
            addedRecipesLiveData.postValue(addedRecipesLiveData.value?.minus(recipe))
        }
    }

    fun listAddedRecipes(){
        viewModelScope.launch(Dispatchers.IO) {
            val recipes = recipeCameraRepository.listAddedRecipes()
            addedRecipesLiveData.postValue(recipes)
        }
    }
}