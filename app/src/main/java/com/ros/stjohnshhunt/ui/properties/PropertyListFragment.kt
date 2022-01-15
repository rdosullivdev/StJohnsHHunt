package com.ros.stjohnshhunt.ui.properties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ros.stjohnshhunt.adapters.HouseListAdapter
import com.ros.stjohnshhunt.databinding.FragmentPropertyListBinding
import com.ros.stjohnshhunt.viewmodels.PropertiesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertyListFragment : Fragment() {

    private val viewModel: PropertiesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPropertyListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = HouseListAdapter()
        binding.propertyList.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: HouseListAdapter) {
        viewModel.houses.observe(viewLifecycleOwner) { houses ->
            adapter.submitList(houses)
        }
    }
}