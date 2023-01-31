package finki.ukim.mk.cookitup.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import finki.ukim.mk.cookitup.adapters.RecipeApiAdapter
import finki.ukim.mk.cookitup.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchViewModel =
            ViewModelProvider(this, SearchViewModelFactory(requireContext()))[SearchViewModel::class.java]

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var adapter:RecipeApiAdapter = RecipeApiAdapter(onClickListener = {/*to do*/})
        binding.list.adapter = adapter
        searchViewModel.getRecipesLiveData().observe(viewLifecycleOwner) {
            adapter.setItemsToAdapter(it)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchViewModel =
            ViewModelProvider(this, SearchViewModelFactory(requireContext()))[SearchViewModel::class.java]
        binding.button.setOnClickListener { searchViewModel.search(binding.editQuery.text.toString()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}