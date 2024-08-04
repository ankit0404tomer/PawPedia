package com.paw.pedia.domain.usecase.dogBreeds

import com.paw.pedia.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow

interface UseCase {
    fun getDogBreeds(): Flow<List<DogBreed>>
}