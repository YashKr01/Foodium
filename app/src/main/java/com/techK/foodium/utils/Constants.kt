package com.techK.foodium.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.techK.foodium.BuildConfig
import com.techK.foodium.domain.entities.Category

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

    const val BASE_URL = "https://api.spoonacular.com/"

    const val QUERY_TYPE = "type"
    const val QUERY_API_KEY = "apiKey"

    const val USER_PREFERENCES_NAME = "user_preferences"

    val REFRESH_LIST = booleanPreferencesKey("refresh_required")
    val SORT_PREFERENCE = stringPreferencesKey("sort_preference")
    
    const val DATABASE_NAME = "foodium_database"

    val API_KEY = listOf(
        BuildConfig.API_KEY1,
        BuildConfig.API_KEY2,
        BuildConfig.API_KEY3,
        BuildConfig.API_KEY4
    )

}