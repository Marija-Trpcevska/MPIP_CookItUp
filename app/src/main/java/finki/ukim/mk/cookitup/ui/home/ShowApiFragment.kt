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
import finki.ukim.mk.cookitup.adapters.RecipeApiShowAdapter
import finki.ukim.mk.cookitup.databinding.FragmentShowApiBinding


class ShowApiFragment : Fragment() {
    private var _binding: FragmentShowApiBinding? = null
    private val binding get() = _binding!!
    private val showApiViewModel by activityViewModels<ShowApiViewModel>{
        ShowApiViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter =  RecipeApiShowAdapter(onClickListener = {
                recipe ->
            val font = ResourcesCompat.getFont(requireContext(), R.font.comfortaa)
            val snack = Snackbar.make(binding.layoutShowApi,getString(R.string.recipe_removed_snackbar), Snackbar.LENGTH_SHORT)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                .setAction(getString(R.string.recipe_undo_snackbar)){showApiViewModel.addRecipe(recipe)}
            showApiViewModel.deleteRecipe(recipe)
            snack.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).typeface = font
            snack.anchorView = requireActivity().findViewById(R.id.nav_view)
            snack.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.color_secondary))
            snack.show()
        })
        binding.addedList.adapter = adapter
        showApiViewModel.getAddedRecipesLiveData().observe(viewLifecycleOwner) {
            adapter.setItemsToAdapter(it)
        }
        showApiViewModel.listAddedRecipes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}