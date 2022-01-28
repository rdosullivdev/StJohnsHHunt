package com.ros.stjohnshhunt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ros.stjohnshhunt.data.House
import com.ros.stjohnshhunt.data.SearchConfig
import com.ros.stjohnshhunt.databinding.ListItemPropertyBinding
import com.ros.stjohnshhunt.databinding.ListItemSearchBinding
import com.ros.stjohnshhunt.ui.properties.PropertyListFragmentDirections

class SearchesListAdapter: ListAdapter<SearchConfig, RecyclerView.ViewHolder>(SearchConfigDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchConfigViewHolder(
            ListItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val house = getItem(position)
        (holder as SearchConfigViewHolder).bind(house)
    }

    class SearchConfigViewHolder(
        private val binding: ListItemSearchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.search?.let { search ->
                    //navigateToPlant(house, it)
                }
            }
        }

//        private fun navigateToSearch(search: SearchConfig, view: View) {
//            val direction = PropertyListFragmentDirections
//                .actionPropertyListFragmentToHouseDetailFragment(house.houseId)
//            view.findNavController().navigate(direction)
//        }

        fun bind(item: SearchConfig) {
            binding.apply {
                search = item
                executePendingBindings()
            }
        }
    }
}

private class SearchConfigDiffCallback : DiffUtil.ItemCallback<SearchConfig>() {

    override fun areItemsTheSame(oldItem: SearchConfig, newItem: SearchConfig): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchConfig, newItem: SearchConfig): Boolean {
        return oldItem == newItem
    }
}