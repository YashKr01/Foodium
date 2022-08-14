package com.example.foodium.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.foodium.data.network.model.Step
import com.example.foodium.databinding.ItemRecipeInstructionBinding

class InstructionsAdapter :
    ListAdapter<Step, InstructionsAdapter.InstructionViewHolder>(instructionComparator) {

    class InstructionViewHolder(private val binding: ItemRecipeInstructionBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(item:Step) {
                binding.txtRecipeStep.text = "${item.number}. ${item.step}"
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InstructionViewHolder(
        ItemRecipeInstructionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        val instructionComparator = object : DiffUtil.ItemCallback<Step>() {
            override fun areItemsTheSame(
                oldItem: Step,
                newItem: Step,
            ): Boolean = oldItem.number == newItem.number

            override fun areContentsTheSame(
                oldItem: Step,
                newItem: Step,
            ): Boolean = oldItem == newItem

        }

    }

}