package finki.ukim.mk.cookitup.ui.add

import android.content.ContentResolver
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import finki.ukim.mk.cookitup.R
import finki.ukim.mk.cookitup.adapters.IngredientsAdapter
import finki.ukim.mk.cookitup.databinding.FragmentWrittenAddBinding
import finki.ukim.mk.cookitup.domain.add.model.RecipeWritten
import finki.ukim.mk.cookitup.helpers.photopicker.PickSinglePhotoContract
import finki.ukim.mk.cookitup.ui.home.ShowWrittenViewModel
import finki.ukim.mk.cookitup.ui.home.ShowWrittenViewModelFactory
import io.github.muddz.styleabletoast.StyleableToast


class AddWrittenFragment: Fragment() {

    private var _binding: FragmentWrittenAddBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val writtenRecipeImageViewModel : WrittenRecipeImageViewModel by activityViewModels()
    private val showWrittenViewModel by activityViewModels<ShowWrittenViewModel> {
        ShowWrittenViewModelFactory(requireContext())
    }
    private lateinit var noImage: Uri

    private val singlePhotoPickerLauncher = registerForActivityResult(PickSinglePhotoContract()) { imageUri: Uri? ->
        imageUri?.let(writtenRecipeImageViewModel::setImageUri)
        if(imageUri != null){
            binding.showPickedImage.setImageBitmap(writtenRecipeImageViewModel.viewState.value.imageBitmap)
            binding.showPickedImage.visibility = View.VISIBLE
            binding.showPickedImage.tag = imageUri
            binding.imagePicker.apply {
                icon = null
                backgroundTintList = null
                background = null
            }
        }
        else{
            binding.showPickedImage.setImageBitmap(null)
            binding.showPickedImage.setImageDrawable(null)
            binding.showPickedImage.visibility = View.INVISIBLE
            binding.showPickedImage.tag =
            Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + requireContext().resources.getResourcePackageName(R.drawable.ic_baseline_image_not_supported_24)
                    + '/' + requireContext().resources.getResourceTypeName(R.drawable.ic_baseline_image_not_supported_24)
                    + '/' + requireContext().resources.getResourceEntryName(R.drawable.ic_baseline_image_not_supported_24) )
            binding.imagePicker.apply {
                icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_image_search_24)
                backgroundTintList = ColorStateList.valueOf(context.getColor(androidx.cardview.R.color.cardview_light_background))
                background = ContextCompat.getDrawable(context, R.drawable.border_dashed)
        }
        }
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                singlePhotoPickerLauncher.launch()
            }
            else
                StyleableToast.makeText(requireContext(), "Gallery access permission denied", Toast.LENGTH_SHORT, R.style.Toast).show()
        }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkGalleryPermissions(){
        if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        else {
            singlePhotoPickerLauncher.launch()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWrittenAddBinding.inflate(inflater, container, false)
        noImage = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + requireContext().resources.getResourcePackageName(R.drawable.ic_baseline_image_not_supported_24)
                    + '/' + requireContext().resources.getResourceTypeName(R.drawable.ic_baseline_image_not_supported_24)
                    + '/' + requireContext().resources.getResourceEntryName(R.drawable.ic_baseline_image_not_supported_24) )
        binding.showPickedImage.tag = noImage
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = IngredientsAdapter()
        binding.recipeIngredients.adapter = adapter
        binding.btnAddIngredient.setOnClickListener {
            adapter.addItemToAdapter(binding.recipeIngredient.text.toString())
            binding.recipeIngredient.text = null
        }
        binding.imagePicker.setOnClickListener{
            checkGalleryPermissions()
        }

        binding.fab.setOnClickListener{
            if(binding.recipeTitle.text.isEmpty() || adapter.getData().isEmpty()){
                if(binding.recipeTitle.text.isEmpty()){binding.recipeTitle.error = "Recipe title is required"}
                if(adapter.getData().isEmpty()){binding.recipeIngredient.error = "Recipe instructions are required"}
            }
            else {
                binding.recipeIngredient.error = null
                val recipe =
                    RecipeWritten(
                        0,
                        binding.recipeTitle.text.toString(),
                        binding.showPickedImage.tag.toString() ,
                        adapter.getData(),
                        binding.recipeInstructions.text.toString(),
                        binding.recipeMealType.checkedChipIds.map { chip: Int -> binding.recipeMealType.findViewById<Chip>(chip).text.toString()} as ArrayList<String>
                    )
                showWrittenViewModel.addRecipe(recipe)
                val snack = Snackbar.make(binding.recipeAddLayout, "Recipe added to your collection", Snackbar.LENGTH_SHORT)
                val font = ResourcesCompat.getFont(requireContext(), R.font.comfortaa)
                snack.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).typeface = font
                snack.show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}