package finki.ukim.mk.cookitup.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import finki.ukim.mk.cookitup.domain.add.repository.RecipeWrittenRepository
import finki.ukim.mk.cookitup.domain.add.room.RoomRecipeWrittenDataSource
import finki.ukim.mk.cookitup.domain.room.AppDatabase

class ShowWrittenViewModelFactory (private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RecipeWrittenRepository::class.java)
            .newInstance(
                RecipeWrittenRepository(
                    RoomRecipeWrittenDataSource(AppDatabase.getDatabase(context).recipeWrittenDao())
                )
            )
    }
}