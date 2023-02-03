package finki.ukim.mk.cookitup.ui.add

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import finki.ukim.mk.cookitup.R
import finki.ukim.mk.cookitup.databinding.FragmentCameraAddBinding
import finki.ukim.mk.cookitup.domain.add.model.RecipeCamera
import finki.ukim.mk.cookitup.helpers.drawableToBitmap
import java.io.File
import java.io.IOException


class AddCameraFragment: Fragment() {
    private var _binding: FragmentCameraAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mainFile : File

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val path = mainFile.absolutePath
                val bitmap = BitmapFactory.decodeFile(path)
                binding.showPhoto.setImageBitmap(bitmap.asImageBitmap().asAndroidBitmap())
                binding.showPhoto.visibility = View.VISIBLE
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
            Toast.makeText(context, "There was a problem with saving the photo...",Toast.LENGTH_SHORT).show()
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
            else
                Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cameraTaker.setOnClickListener{
            val filename = System.currentTimeMillis().toString() + ".jpg"
            mainFile = File(requireContext().filesDir, filename)
            checkCameraPermission()
        }
        binding.photoFab.setOnClickListener{
            val image = drawableToBitmap(binding.showPhoto,requireContext())
            val recipe = RecipeCamera(
                binding.cameraTitle.text.toString(),
                image.asImageBitmap(),
                binding.photoNotes.text.toString(),
                binding.recipeMealType2.checkedChipIds.map { chip: Int -> binding.recipeMealType2.findViewById<Chip>(chip).text.toString()} as ArrayList<String>
            )
            //I POTOA ADD VO BAZA
            Snackbar.make(binding.recipeCameraLayout, "Recipe added to your collection", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}