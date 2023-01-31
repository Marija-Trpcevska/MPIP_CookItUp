package finki.ukim.mk.cookitup.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import finki.ukim.mk.cookitup.domain.search.repository.RecipeApiRepository
import finki.ukim.mk.cookitup.domain.search.retrofit.RecipeDbApiProvider
import finki.ukim.mk.cookitup.domain.search.retrofit.RetrofitRecipeDataSource

class SearchViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RecipeApiRepository::class.java)
            .newInstance(
                RecipeApiRepository(RetrofitRecipeDataSource(RecipeDbApiProvider.getMovieDbApi())
                ))
    }
}