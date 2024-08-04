package com.paw.pedia.data.remote

import com.paw.pedia.data.Resource
import com.paw.pedia.domain.model.DogBreed

interface RemoteSource {
    suspend fun getDogBreeds(): Resource<List<DogBreed>>
    suspend fun getDogBreedImages(breedName: String): Resource<List<String>>
}

