package com.recipes.app_view.ui.recipesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.recipes.app_view.RecipeId
import com.recipes.app_view.adapters.OnRecipeListener
import com.recipes.app_view.adapters.RecipeRecyclerAdapter
import com.recipes.app_view.databinding.FragmentRecipeSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeSearchFragment : Fragment(), OnRecipeListener {

    companion object {
        private const val TAG = "RecipeSearchFragment"
    }

    private var _binding: FragmentRecipeSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recyclerAdapter: RecipeRecyclerAdapter

    private val viewModel: RecipeSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchView()
        initRecyclerView()

        // TODO #2: Display search results from RecipeSearchViewModel.
        //  populate item_recipe_list.xml
        //  bind it in RecipeViewHolder
        //  It should match the image in screenshots/view_search_results.png
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initSearchView() {
        binding.searchView.setIconifiedByDefault(false)
    }

    private fun initRecyclerView() {
        recyclerAdapter = RecipeRecyclerAdapter(this)
        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onRecipeClick(recipeId: String, title: String) {
        findNavController().navigate(RecipeId(recipeId, title = title))
    }
}