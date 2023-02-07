package finki.ukim.mk.cookitup.ui.add

import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import finki.ukim.mk.cookitup.R
import finki.ukim.mk.cookitup.adapters.IngredientsAdapter
import finki.ukim.mk.cookitup.databinding.FragmentWrittenAddBinding
import finki.ukim.mk.cookitup.domain.add.model.RecipeWritten
import finki.ukim.mk.cookitup.helpers.PickSinglePhotoContract
import finki.ukim.mk.cookitup.helpers.drawableToBitmap


class AddWrittenFragment: Fragment() {

    private var _binding: FragmentWrittenAddBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val writtenRecipeImageViewModel : WrittenRecipeImageViewModel by activityViewModels()

    private val singlePhotoPickerLauncher = registerForActivityResult(PickSinglePhotoContract()) { imageUri: Uri? ->
        imageUri?.let(writtenRecipeImageViewModel::setImageUri)
        if(imageUri != null){
            binding.showPickedImage.setImageBitmap(writtenRecipeImageViewModel.viewState.value.imageBitmap)
            binding.showPickedImage.visibility = View.VISIBLE
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
                Toast.makeText(context, "Gallery access permission denied", Toast.LENGTH_SHORT).show()
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

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = IngredientsAdapter()
        binding.recipeIngredients.adapter = adapter
        binding.btnAddIngredient.setOnClickListener {
            adapter.addItemToAdapter(binding.recipeIngredient.text.toString())
        }
        binding.imagePicker.setOnClickListener{
            checkGalleryPermissions()
        }

        binding.fab.setOnClickListener{
            val image = drawableToBitmap(binding.showPickedImage,requireContext())
            val recipe =
            RecipeWritten(
                binding.recipeTitle.text.toString(),
                image ,
                adapter.getData(),
                binding.recipeInstructions.text.toString(),
                binding.recipeMealType.checkedChipIds.map { chip: Int -> binding.recipeMealType.findViewById<Chip>(chip).text.toString()} as ArrayList<String>
            )
            //I POTOA ADD VO BAZA
            Snackbar.make(binding.recipeAddLayout, "Recipe added to your collection", Snackbar.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}