package com.example.foodium.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodium.databinding.ActivityRecipeDetailsBinding

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

}