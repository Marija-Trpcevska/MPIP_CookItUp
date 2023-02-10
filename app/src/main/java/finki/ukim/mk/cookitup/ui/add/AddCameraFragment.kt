package finki.ukim.mk.cookitup.ui.add

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import finki.ukim.mk.cookitup.R
import finki.ukim.mk.cookitup.databinding.FragmentCameraAddBinding
import finki.ukim.mk.cookitup.domain.add.model.RecipeCamera
import finki.ukim.mk.cookitup.ui.home.ShowCameraViewModel
import finki.ukim.mk.cookitup.ui.home.ShowCameraViewModelFactory
import io.github.muddz.styleabletoast.StyleableToast
import java.io.File
import java.io.IOException


class AddCameraFragment: Fragment() {
    private var _binding: FragmentCameraAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val showCameraViewModel by activityViewModels<ShowCameraViewModel>{
        ShowCameraViewModelFactory(requireContext())
    }
    private lateinit var mainFile : File
    private lateinit var noImage : Uri

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val path = mainFile.absolutePath
                val bitmap = BitmapFactory.decodeFile(path)
                binding.showPhoto.setImageBitmap(bitmap)
                binding.showPhoto.visibility = View.VISIBLE
                binding.showPhoto.tag = path
                binding.cameraTaker.apply {
                    icon = null
                    backgroundTintList = null
                    background = null
                }
            }
            else if (result.resultCode ==  Activity.RESULT_CANCELED){
                    binding.showPhoto.setImageBitmap(null)
                    binding.showPhoto.setImageDrawable(null)
                    binding.showPhoto.visibility = View.INVISIBLE
                    binding.showPhoto.tag = noImage
                    binding.cameraTaker.apply {
                        icon = ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_add_a_photo_from_camera
                        )
                        backgroundTintList =
                            ColorStateList.valueOf(context.getColor(androidx.cardview.R.color.cardview_light_background))
                        background = ContextCompat.getDrawable(context, R.drawable.border_dashed)
                    }
            }
        }

    private fun chooseFromCamera() {
        val intent = Intent()
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
        intent.flags = (Intent.FLAG_GRANT_READ_URI_PERMISSION
                or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        if (!mainFile.exists()) try {
            mainFile.createNewFile()
        } catch (e: IOException) {
            StyleableToast.makeText(requireContext(), "There was a problem with saving the photo...",Toast.LENGTH_SHORT, R.style.Toast).show()
            e.printStackTrace()
        }
        val imageUri = FileProvider.getUriForFile(
            requireContext(),
            "finki.ukim.mk.cookitup.fileProvider", mainFile
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        resultLauncher.launch(intent)
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                chooseFromCamera()
                }
            else{
                StyleableToast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT, R.style.Toast).show()
            }
        }

    private fun checkCameraPermission(){
        if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermission.launch(android.Manifest.permission.CAMERA)
        }
        else {
            chooseFromCamera()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraAddBinding.inflate(inflater, container, false)
        noImage = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE
                        + "://" + requireContext().resources.getResourcePackageName(R.drawable.ic_baseline_image_not_supported_24)
                        + '/' + requireContext().resources.getResourceTypeName(R.drawable.ic_baseline_image_not_supported_24)
                        + '/' + requireContext().resources.getResourceEntryName(R.drawable.ic_baseline_image_not_supported_24) )
        binding.showPhoto.tag = noImage

        binding.cameraTaker.setOnClickListener{
            val filename = System.currentTimeMillis().toString() + ".jpg"
            mainFile = File(requireContext().filesDir, filename)
            checkCameraPermission()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.photoFab.setOnClickListener{
            if(binding.cameraTitle.text.isEmpty() || binding.showPhoto.tag.equals(noImage)){
                if(binding.cameraTitle.text.isEmpty()){binding.cameraTitle.error = "Recipe title is required"}
                if(binding.showPhoto.tag.equals(noImage)){binding.cameraTakerLabel.error = "Recipe photo is required"}
            }
            else {
                binding.cameraTakerLabel.error = null
                val recipe = RecipeCamera(
                    0,
                    binding.cameraTitle.text.toString(),
                    binding.showPhoto.tag.toString(),
                    binding.photoNotes.text.toString(),
                    binding.recipeMealType2.checkedChipIds.map { chip: Int -> binding.recipeMealType2.findViewById<Chip>(chip).text.toString()} as ArrayList<String>
                )
                showCameraViewModel.addRecipe(recipe)
                val snack = Snackbar.make(binding.recipeCameraLayout, "Recipe added to your collection",Snackbar.LENGTH_SHORT)
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