package com.paw.pedia.domain.usecase.favouriteDogBreeds

import com.paw.pedia.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow


interface UseCase {
    fun getFavouriteDogBreeds(): Flow<List<DogBreed>>
    suspend fun addToFavourites(name: String, isFavourite: Boolean)
}