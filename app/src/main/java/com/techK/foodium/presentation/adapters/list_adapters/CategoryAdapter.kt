package com.techK.foodium.presentation.adapters.list_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.techK.foodium.databinding.ItemCategoryBinding
import com.techK.foodium.domain.entities.Category

class CategoryAdapter(
    private var selectedPosition: Int = 0,
    private val onCategoryClick: (Category, Int) -> Unit,
) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CATEGORY_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val category = getItem(position)
        if (category != null) holder.bind(category, position)

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

        fun bind(item: Category, position: Int) {
            binding.itemChip.text = item.title
            when (selectedPosition) {
                position -> binding.itemChip.isChecked = true
                else -> binding.itemChip.isChecked = false
            }
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