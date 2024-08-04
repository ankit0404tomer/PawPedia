package com.paw.pedia.domain.usecase.favouriteDogBreeds

import com.paw.pedia.data.repository.DataSource
import com.paw.pedia.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouriteDogBreedsUseCase @Inject constructor(
    private val dataRepository: DataSource
) : UseCase {
    override fun getFavouriteDogBreeds(): Flow<List<DogBreed>> {
        return dataRepository.getFavouriteDogBreeds()
    }

    override suspend fun addToFavourites(name: String, isFavourite: Boolean) {
        dataRepository.updateDogBreeds(name = name, isFavourite = isFavourite)
    }
}