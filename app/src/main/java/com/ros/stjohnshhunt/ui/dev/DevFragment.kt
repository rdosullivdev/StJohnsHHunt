package com.ros.stjohnshhunt.ui.dev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ros.stjohnshhunt.R
import com.ros.stjohnshhunt.databinding.FragmentDevBinding
import com.ros.stjohnshhunt.viewmodels.DevViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DevFragment : Fragment() {

    private val devViewModel: DevViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentDevBinding>(
            inflater,
            R.layout.fragment_dev,
            container,
            false
        ).apply {
            viewModel = devViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }
}
