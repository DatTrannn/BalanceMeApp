package com.nutrition.balanceme.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nutrition.balanceme.domain.models.Item
import com.nutrition.balanceme.domain.models.Profile
import com.nutrition.balanceme.domain.models.Recipe
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@TypeConverters(Converters::class)
@Database(entities = [Profile::class, Item::class, Recipe::class], version = 1, exportSchema = false)
abstract class FoodiesDatabase: RoomDatabase() {

    abstract fun getUserDao(): UserDao

    abstract fun getItemDao(): RecipesDao

    companion object{
        const val DB_NAME="foodies"
    }
}