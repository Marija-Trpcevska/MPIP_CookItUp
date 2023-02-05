package finki.ukim.mk.cookitup.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import finki.ukim.mk.cookitup.adapters.RecipeApiAdapter
import finki.ukim.mk.cookitup.databinding.FragmentSearchBinding

class SearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var searchViewModel : SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchViewModel =
            ViewModelProvider(this, SearchViewModelFactory(requireContext()))[SearchViewModel::class.java]

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //I POTOA ADD VO BAZA
        val adapter = RecipeApiAdapter(onClickListener = {Snackbar.make(binding.searchLayout,"Recipe added to your collection", Snackbar.LENGTH_SHORT).show()})
        binding.list.adapter = adapter
        searchViewModel.getRecipesLiveData().observe(viewLifecycleOwner) {
            adapter.setItemsToAdapter(it)
        }
        binding.queryField.clearFocus()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.queryField.setOnQueryTextListener(this)
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