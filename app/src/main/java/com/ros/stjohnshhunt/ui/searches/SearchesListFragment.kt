package com.ros.stjohnshhunt.ui.searches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ros.stjohnshhunt.adapters.SearchesListAdapter
import com.ros.stjohnshhunt.databinding.FragmentSearchesListBinding
import com.ros.stjohnshhunt.viewmodels.PropertySearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchesListFragment : Fragment() {

    private val viewModel: PropertySearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchesListBinding
            .inflate(inflater, container, false)
            .apply {

                val adapter = SearchesListAdapter()
                searchesList.adapter = adapter
                subscribeUi(adapter)

                addSearchFab.setOnClickListener {
                    findNavController().navigate(
                        SearchesListFragmentDirections.actionSearchListFragmentToSearchCreationFragment())
                }
            }
        return binding.root
    }

    private fun subscribeUi(adapter: SearchesListAdapter) {
        viewModel.searches.observe(viewLifecycleOwner) { searches ->
            adapter.submitList(searches)
        }
    }
}

