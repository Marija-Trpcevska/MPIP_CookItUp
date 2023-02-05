package finki.ukim.mk.cookitup.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import finki.ukim.mk.cookitup.domain.add.model.RecipeCamera
import finki.ukim.mk.cookitup.domain.add.model.RecipeWritten

class AddViewModel : ViewModel() {

    private val _writtenRecipe = MutableLiveData<RecipeWritten>()
    fun getWrittenRecipe(): LiveData<RecipeWritten> = _writtenRecipe

    private val _cameraRecipe = MutableLiveData<RecipeCamera>()
    fun getCameraRecipe(): LiveData<RecipeCamera> = _cameraRecipe

    fun setWrittenRecipe(recipe: RecipeWritten){
        _writtenRecipe.value = recipe
    }

    fun getCameraRecipe(recipe: RecipeCamera){
        _cameraRecipe.value = recipe
    }

}