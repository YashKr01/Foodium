package com.example.foodium.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodium.databinding.ItemCategoryBinding

class CategoryAdapter(
    private var selectedPosition: Int = 0,
    private val onCategoryClick: (Category, Int) -> Unit
) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CATEGORY_COMPARATOR) {

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

        holder.binding.root.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                val temp = selectedPosition
                selectedPosition = position
                onCategoryClick(getItem(position), position)
                notifyItemChanged(temp)
                notifyItemChanged(position)
            }
        }

    }

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

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