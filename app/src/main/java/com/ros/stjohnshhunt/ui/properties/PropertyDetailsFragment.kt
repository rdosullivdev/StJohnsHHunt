package com.ros.stjohnshhunt.ui.properties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ros.stjohnshhunt.R
import com.ros.stjohnshhunt.databinding.FragmentPropertyDetailBinding
import com.ros.stjohnshhunt.viewmodels.PropertyDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PropertyDetailsFragment : Fragment() {

    private val propertyDetailViewModel: PropertyDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentPropertyDetailBinding>(
            inflater,
            R.layout.fragment_property_detail,
            container,
            false
        ).apply {
            viewModel = propertyDetailViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }
}
