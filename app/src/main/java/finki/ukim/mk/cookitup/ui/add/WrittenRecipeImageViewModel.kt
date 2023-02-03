package finki.ukim.mk.cookitup.ui.add

import android.app.Application
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream

class WrittenRecipeImageViewModel(application: Application) : AndroidViewModel(application) {
    data class ViewState(
        val imageBitmap: ImageBitmap? = null,
    )

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    fun setImageUri(uri: Uri) {
        viewModelScope.launch {
            getContext().contentResolver.openInputStream(uri)?.use { inputStream: InputStream ->
                _viewState.update { currentState: ViewState ->
                    currentState.copy(imageBitmap = BitmapFactory.decodeStream(inputStream).asImageBitmap())
                }
            }
        }
    }

    private fun getContext(): Context = getApplication<Application>().applicationContext

}
