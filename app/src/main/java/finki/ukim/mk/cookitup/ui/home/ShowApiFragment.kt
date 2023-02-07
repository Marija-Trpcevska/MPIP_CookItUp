package finki.ukim.mk.cookitup.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import finki.ukim.mk.cookitup.adapters.RecipeApiShowAdapter
import finki.ukim.mk.cookitup.databinding.FragmentShowApiBinding

class ShowApiFragment : Fragment() {
    private var _binding: FragmentShowApiBinding? = null
    private val binding get() = _binding!!
    private var showApiViewModel: ShowApiViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowApiBinding.inflate(inflater, container, false)
        showApiViewModel =
            activity?.let{ViewModelProvider(it, ShowApiViewModelFactory(requireContext()))}?.get(ShowApiViewModel::class.java)
//        showApiViewModel =
//            ViewModelProvider(requireActivity(), ShowApiViewModelFactory(requireContext()))[ShowApiViewModel::class.java]
        showApiViewModel?.listAddedRecipes()
        //I POTOA DELETE OD BAZA
        val adapter =  RecipeApiShowAdapter(onClickListener = {
            showApiViewModel?.deleteRecipe(it)
            Snackbar.make(binding.layoutShowApi,"Recipe removed from your collection", Snackbar.LENGTH_SHORT).show()
        })

        binding.addedList.adapter = adapter
        showApiViewModel?.getAddedRecipesLiveData()?.observe(viewLifecycleOwner) {
            adapter.setItemsToAdapter(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showApiViewModel?.listAddedRecipes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}