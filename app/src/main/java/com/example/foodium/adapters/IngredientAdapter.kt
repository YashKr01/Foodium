package com.example.foodium.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.foodium.data.network.model.ExtendedIngredient
import com.example.foodium.databinding.ItemIngredientBinding
import com.example.foodium.utils.Constants.BASE_IMAGE_URL

class IngredientAdapter :
    ListAdapter<ExtendedIngredient, IngredientAdapter.IngredientViewHolder>(ingredientComparator) {

    class IngredientViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ExtendedIngredient) {
            binding.apply {
                txtIngredientConsistency.text = item.consistency
                txtIngredientName.text = item.name
                txtIngredientQuantity.text =
                    "${item.amount.toString().substring(0, 3)} ${item.unit}"

                Glide.with(itemView)
                    .load(BASE_IMAGE_URL + item.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgIngredient)

            }
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

        val ingredientComparator = object : DiffUtil.ItemCallback<ExtendedIngredient>() {
            override fun areItemsTheSame(
                oldItem: ExtendedIngredient,
                newItem: ExtendedIngredient
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ExtendedIngredient,
                newItem: ExtendedIngredient
            ): Boolean = oldItem == newItem

        }

    }

}