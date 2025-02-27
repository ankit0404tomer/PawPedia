package com.paw.pedia.data.local.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.paw.pedia.domain.model.DogBreed
import com.paw.pedia.domain.model.DogBreedImages
import com.paw.pedia.utils.Converters


@Database(version = 1, entities = [DogBreed::class, DogBreedImages::class], exportSchema = false)
@TypeConverters(Converters::class)
abstract class DogBreedsDatabase : RoomDatabase() {

    abstract fun dogBreedsDao(): DogBreedsDao
}