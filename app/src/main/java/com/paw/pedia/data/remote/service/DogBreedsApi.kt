package com.paw.pedia.data.remote.service

import com.paw.pedia.data.remote.dto.DogBreedImagesResponse
import com.paw.pedia.data.remote.dto.DogBreedResponse
import com.paw.pedia.data.remote.dto.DogBreedSingleImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedsApi {
    @GET("breeds/list/all")
    suspend fun fetchDogBreeds(): Response<DogBreedResponse>

    @GET("breed/{breed_name}/images/random/4")
    suspend fun fetchDogBreedImages(@Path("breed_name") breedName: String): Response<DogBreedImagesResponse>

    @GET("breed/{breed_name}/images/random")
    suspend fun fetchDogBreedSingleImage(@Path("breed_name") breedName: String): Response<DogBreedSingleImageResponse>
}