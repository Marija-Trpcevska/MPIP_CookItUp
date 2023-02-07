package finki.ukim.mk.cookitup.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import finki.ukim.mk.cookitup.domain.search.model.RecipeApi
import finki.ukim.mk.cookitup.domain.search.repository.RecipeApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val recipeApiRepository: RecipeApiRepository) : ViewModel() {

    private val recipesLiveData = MutableLiveData<List<RecipeApi>>()

    fun getRecipesLiveData(): LiveData<List<RecipeApi>> = recipesLiveData

    fun search(query: String){
        viewModelScope.launch(Dispatchers.IO){
            val recipes = recipeApiRepository.searchForRecipes(query)
            recipesLiveData.postValue(recipes)
        }
    }

    fun listRecipesInCache(){
        viewModelScope.launch(Dispatchers.IO) {
            val recipes = recipeApiRepository.listRecipesInCache()
            recipesLiveData.postValue(recipes)
        }
    }
}