package com.ros.stjohnshhunt.ui.searches

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ros.stjohnshhunt.R
import com.ros.stjohnshhunt.databinding.FragmentSearchCreationBinding
import com.ros.stjohnshhunt.viewmodels.PropertySearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCreationFragment : Fragment() {

    private val propertySearchViewModel: PropertySearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentSearchCreationBinding>(
            inflater,
            R.layout.fragment_search_creation,
            container,
            false
        ).apply {
            viewModel = propertySearchViewModel
            lifecycleOwner = viewLifecycleOwner

            searchCta.setOnClickListener {
                propertySearchViewModel.saveSearch(
                    searchName.text.toString(),
                    searchBedCount.text.toString(),
                    searchBathCount.text.toString(),
                    searchPriceMin.text.toString(),
                    searchPriceMax.text.toString()
                )
                findNavController().navigateUp()
            }
        }
        return binding.root
    }
}

