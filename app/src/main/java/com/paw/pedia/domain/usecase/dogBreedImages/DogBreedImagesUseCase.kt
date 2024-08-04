package com.paw.pedia.domain.usecase.dogBreedImages

import com.paw.pedia.data.Resource
import com.paw.pedia.data.repository.DataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DogBreedImagesUseCase @Inject constructor(
    private val dataRepository: DataSource
) : UseCase {
    override suspend fun getDogBreedImages(breed: String): Flow<Resource<List<String>>> {
        return dataRepository.getDogBreedImages(breed)
    }
}