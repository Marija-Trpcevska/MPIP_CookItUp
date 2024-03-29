package finki.ukim.mk.cookitup.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import finki.ukim.mk.cookitup.R
import finki.ukim.mk.cookitup.adapters.RecipeApiAdapter
import finki.ukim.mk.cookitup.databinding.FragmentSearchBinding
import finki.ukim.mk.cookitup.ui.home.ShowApiViewModel
import finki.ukim.mk.cookitup.ui.home.ShowApiViewModelFactory


class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var searchViewModel : SearchViewModel
    private lateinit var showApiViewModel: ShowApiViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchViewModel =
            ViewModelProvider(this, SearchViewModelFactory(requireContext()))[SearchViewModel::class.java]

        showApiViewModel =
            ViewModelProvider(requireActivity(), ShowApiViewModelFactory(requireContext()))[ShowApiViewModel::class.java]

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = RecipeApiAdapter(onClickListener = {
            showApiViewModel.addRecipe(it)
            val font = ResourcesCompat.getFont(requireContext(), R.font.comfortaa)
            val snack = Snackbar.make(binding.searchLayout,"Recipe added to your collection", Snackbar.LENGTH_SHORT)
            snack.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).typeface = font
            snack.anchorView = requireActivity().findViewById(R.id.nav_view)
            snack.show()
        })
        binding.list.adapter = adapter
        searchViewModel.getRecipesLiveData().observe(viewLifecycleOwner) {
            adapter.setItemsToAdapter(it)
        }
        binding.queryField.clearFocus()

//        val font = ResourcesCompat.getFont(requireContext(), finki.ukim.mk.cookitup.R.font.amatic_sc_bold)
//        val searchViewTextView = binding.queryField.findViewById<SearchAutoComplete>(android.R.id.search_src_text)
//        searchViewTextView.typeface = font

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.queryField.setOnQueryTextListener(this)
        searchViewModel.listRecipesInCache()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        searchViewModel.search(binding.queryField.query.toString())
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return false
    }
}