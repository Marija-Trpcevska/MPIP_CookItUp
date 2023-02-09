package finki.ukim.mk.cookitup.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import finki.ukim.mk.cookitup.domain.add.repository.RecipeCameraRepository
import finki.ukim.mk.cookitup.domain.add.room.RoomRecipeCameraDataSource
import finki.ukim.mk.cookitup.domain.room.AppDatabase


class ShowCameraViewModelFactory(private val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RecipeCameraRepository::class.java)
            .newInstance(
                RecipeCameraRepository(
                    RoomRecipeCameraDataSource(AppDatabase.getDatabase(context).recipeCameraDao())
                )
            )
    }
}