package com.ros.stjohnshhunt.ui.properties

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.ros.stjohnshhunt.R
import com.ros.stjohnshhunt.data.House
import com.ros.stjohnshhunt.databinding.FragmentPropertyDetailBinding
import com.ros.stjohnshhunt.viewmodels.PropertyDetailsViewModel
import com.ros.stjohnshhunt.workers.SyncHousesWorker
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

            detailsCallback = DetailsCallback { house ->
                house?.let {
                    val intent = Intent(Intent.ACTION_VIEW, house.getDetailsUrl())
                    startActivity(Intent.createChooser(intent, null))
                }
            }
            addressCallback = AddressCallback { house ->
                house?.let {
                    val sendIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = house.getGMapsUri()
                    }
                    startActivity(Intent.createChooser(sendIntent, null))
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WorkManager.getInstance(context!!).enqueue(
            OneTimeWorkRequest.from(SyncHousesWorker::class.java)
        )
    }

    fun interface AddressCallback {
        fun load(house: House?)
    }
    fun interface DetailsCallback {
        fun load(house: House?)
    }
}
