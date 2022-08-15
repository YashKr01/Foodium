package com.example.foodium.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.foodium.BuildConfig
import com.example.foodium.adapters.Category

object Constants {

    val categoryList = listOf(
        Category("Trending"),
        Category("Main Course"),
        Category("Side Dish"),
        Category("Dessert"),
        Category("Appetizer"),
        Category("Bread"),
        Category("Breakfast"),
        Category("Salad"),
        Category("Soup"),
        Category("Beverage"),
        Category("Sauce"),
        Category("Snack")
    )

    const val BASE_URL = "https://api.spoonacular.com"

    const val QUERY_TYPE = "type"
    const val QUERY_API_KEY = "apiKey"
    const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"

    const val USER_PREFERENCES_NAME = "user_preferences"

    val REFRESH_LIST = booleanPreferencesKey("refresh_required")
    val SORT_PREFERENCE = stringPreferencesKey("sort_preference")

    const val JOKE_TABLE_NAME = "joke_table"
    const val RECIPE_TABLE_NAME = "recipes_table"

    const val DATABASE_NAME = "foodium_database"

    val API_KEY = listOf(
        BuildConfig.API_KEY1,
        BuildConfig.API_KEY2,
        BuildConfig.API_KEY3,
        BuildConfig.API_KEY4,
    )

}