package finki.ukim.mk.cookitup.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import finki.ukim.mk.cookitup.R
import finki.ukim.mk.cookitup.adapters.RecipeWrittenShowAdapter
import finki.ukim.mk.cookitup.databinding.FragmentShowWrittenBinding

class ShowWrittenFragment : Fragment() {
    private var _binding: FragmentShowWrittenBinding? = null
    private val binding get() = _binding!!
    private val showWrittenViewModel by activityViewModels<ShowWrittenViewModel>{
        ShowWrittenViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowWrittenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RecipeWrittenShowAdapter(onClickListener = {
                recipe ->
            val font = ResourcesCompat.getFont(requireContext(), R.font.comfortaa)
            val snack = Snackbar.make(binding.layoutShowWritten,getString(R.string.recipe_removed_snackbar), Snackbar.LENGTH_SHORT)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                .setAction(getString(R.string.recipe_undo_snackbar)){showWrittenViewModel.addRecipe(recipe)}
            showWrittenViewModel.deleteRecipe(recipe)
            snack.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).typeface = font
            snack.anchorView = requireActivity().findViewById(R.id.nav_view)
            snack.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.color_secondary))
            snack.show()
        })
        binding.addedWrittenList.adapter = adapter
        showWrittenViewModel.getAddedRecipesLiveData().observe(viewLifecycleOwner){
            adapter.setItemsToAdapter(it)
        }
        showWrittenViewModel.listAddedRecipes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}