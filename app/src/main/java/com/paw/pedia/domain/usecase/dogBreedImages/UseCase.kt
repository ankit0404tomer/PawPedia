package com.paw.pedia.domain.usecase.dogBreedImages

import com.paw.pedia.data.Resource
import kotlinx.coroutines.flow.Flow


interface UseCase {
    suspend fun getDogBreedImages(breed : String): Flow<Resource<List<String>>>
}