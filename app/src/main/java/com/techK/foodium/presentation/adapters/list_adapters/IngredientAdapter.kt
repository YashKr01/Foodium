package com.techK.foodium.presentation.adapters.list_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.techK.foodium.data.response.ExtendedIngredient
import com.techK.foodium.databinding.ItemIngredientBinding

class IngredientAdapter :
    ListAdapter<ExtendedIngredient, IngredientAdapter.IngredientViewHolder>(INGREDIENT_COMPARATOR) {

    class IngredientViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ExtendedIngredient) {
            binding.ingredient = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder =
        IngredientViewHolder(
            ItemIngredientBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        val INGREDIENT_COMPARATOR = object : DiffUtil.ItemCallback<ExtendedIngredient>() {
            override fun areItemsTheSame(
                oldItem: ExtendedIngredient,
                newItem: ExtendedIngredient,
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ExtendedIngredient,
                newItem: ExtendedIngredient,
            ) = oldItem == newItem

        }

    }

}