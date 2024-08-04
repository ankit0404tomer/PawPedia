package com.paw.pedia.data.repository

import com.paw.pedia.data.Resource
import com.paw.pedia.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getDogBreeds(): Flow<List<DogBreed>>
    suspend fun getDogBreedImages(breedName: String): Flow<Resource<List<String>>>
    suspend fun updateDogBreeds(name: String, isFavourite: Boolean)
    fun getFavouriteDogBreeds(): Flow<List<DogBreed>>
}
