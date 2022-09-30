package com.techK.foodium.presentation.adapters.list_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.techK.foodium.data.response.Step
import com.techK.foodium.databinding.ItemRecipeInstructionBinding

class InstructionsAdapter :
    ListAdapter<Step, InstructionsAdapter.InstructionViewHolder>(INSTRUCTION_COMPARATOR) {

    class InstructionViewHolder(
        private val binding: ItemRecipeInstructionBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Step) {
            binding.step = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        return InstructionViewHolder(
            ItemRecipeInstructionBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val INSTRUCTION_COMPARATOR = object : DiffUtil.ItemCallback<Step>() {
            override fun areItemsTheSame(oldItem: Step, newItem: Step) =
                oldItem.number == newItem.number

            override fun areContentsTheSame(oldItem: Step, newItem: Step) = oldItem == newItem
        }

    }

}