package com.example.sixt.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sixt.databinding.ItemCarDetailBinding
import com.example.sixt.model.data.CarModel


class CarAdapter :
    ListAdapter<CarModel, CarViewHolder>(CarDiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        return CarViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CarViewHolder private constructor(private val binding: ItemCarDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): CarViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCarDetailBinding.inflate(layoutInflater, parent, false)
            return CarViewHolder(binding)
        }
    }

    fun bind(data: CarModel) {
        binding.carResponse = data
        binding.executePendingBindings()
    }
}

class CarDiffUtilCallBack : DiffUtil.ItemCallback<CarModel>() {
    override fun areItemsTheSame(oldItem: CarModel, newItem: CarModel): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: CarModel, newItem: CarModel): Boolean {
        return oldItem == newItem
    }
}


