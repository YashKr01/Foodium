package com.example.foodium.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodium.databinding.ItemCategoryBinding

class CategoryAdapter :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CATEGORY_COMPARATOR) {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CategoryViewHolder(
        ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
        when (selectedPosition) {
            position -> holder.binding.root.isChecked = true
            else -> holder.binding.root.isChecked = false
        }
    }

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {

                    notifyItemChanged(selectedPosition)
                    selectedPosition = position
                    notifyItemChanged(position)

                }
            }
        }

        fun bind(item: Category) {
            binding.itemChip.text = item.title
        }
    }

    companion object {
        val CATEGORY_COMPARATOR = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Category, newItem: Category) =
                oldItem == newItem
        }
    }

}

data class Category(val title: String)