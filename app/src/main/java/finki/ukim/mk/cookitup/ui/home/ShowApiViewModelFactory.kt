package finki.ukim.mk.cookitup.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import finki.ukim.mk.cookitup.domain.room.AppDatabase
import finki.ukim.mk.cookitup.domain.search.repository.RecipeApiRepository
import finki.ukim.mk.cookitup.domain.search.retrofit.RecipeDbApiProvider
import finki.ukim.mk.cookitup.domain.search.retrofit.RetrofitRecipeDataSource
import finki.ukim.mk.cookitup.domain.search.room.RoomRecipeApiDataSource
import finki.ukim.mk.cookitup.helpers.NetworkConnectivity

class ShowApiViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RecipeApiRepository::class.java)
            .newInstance(
                RecipeApiRepository(
                    RetrofitRecipeDataSource(RecipeDbApiProvider.getRecipeDbApi()),
                    RoomRecipeApiDataSource(AppDatabase.getDatabase(context).recipeApiDao()),
                    NetworkConnectivity(context)
                )
            )
    }
}