package com.techK.foodium.domain.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.techK.foodium.BuildConfig

object Constants {

    const val BASE_URL = "https://api.spoonacular.com/"

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