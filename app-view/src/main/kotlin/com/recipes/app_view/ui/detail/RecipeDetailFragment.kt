package com.recipes.app_view.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.toRoute
import com.recipes.app_view.RecipeId
import com.recipes.app_view.databinding.FragmentRecipeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeDetailFragment : Fragment() {

    companion object {
        const val TAG: String = "RecipeDetailFragment"
    }

    private var _binding: FragmentRecipeDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: RecipeDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO #4: Populate fragment_recipe_detail.xml and bind
        //  it to the results from RecipeDetailViewModel.
        //  See the screenshot at screenshots/view_details.png

        setRecipeId()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecipeId() {
        val detailRoute = findNavController().getBackStackEntry<RecipeId>().toRoute<RecipeId>()
        val recipeId: String = detailRoute.id
        Log.v(TAG, "incoming recipe: $recipeId ${detailRoute.title}")
        viewModel.setRecipeId(recipeId)
    }
}