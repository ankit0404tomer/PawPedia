package com.paw.pedia.domain.usecase.dogBreeds

import com.paw.pedia.data.repository.DataSource
import com.paw.pedia.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DogBreedsUseCase @Inject constructor(
    private val dataRepository: DataSource
) : UseCase {
     override fun getDogBreeds(): Flow<List<DogBreed>> {

        return dataRepository.getDogBreeds()
    }
}