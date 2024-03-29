package com.ros.stjohnshhunt.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ros.stjohnshhunt.data.House
import com.ros.stjohnshhunt.databinding.ListItemPropertyBinding

class HouseListAdapter: ListAdapter<House, RecyclerView.ViewHolder>(HouseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HouseViewHolder(
            ListItemPropertyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val house = getItem(position)
        (holder as HouseViewHolder).bind(house)
    }

    class HouseViewHolder(
        private val binding: ListItemPropertyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.house?.let { house ->
                    navigateToPlant(house, it)
                }
            }
        }

        private fun navigateToPlant(house: House, view: View) {
            //todo
        }

        fun bind(item: House) {
            binding.apply {
                house = item
                executePendingBindings()
            }
        }
    }
}

private class HouseDiffCallback : DiffUtil.ItemCallback<House>() {

    override fun areItemsTheSame(oldItem: House, newItem: House): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: House, newItem: House): Boolean {
        return oldItem == newItem
    }
}