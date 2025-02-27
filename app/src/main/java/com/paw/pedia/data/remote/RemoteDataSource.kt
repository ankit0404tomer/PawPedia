package com.paw.pedia.data.remote

import android.util.Log
import com.paw.pedia.data.Resource
import com.paw.pedia.data.remote.dto.DogBreedResponse
import com.paw.pedia.data.remote.helper.DogBreedWithSubBreeds
import com.paw.pedia.data.remote.service.DogBreedsApi
import com.paw.pedia.domain.model.DogBreed
import com.paw.pedia.utils.Constants.COMMA
import com.paw.pedia.utils.Constants.DEFAULT_IS_FAVOURITE
import com.paw.pedia.utils.extensions.asyncAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSource @Inject
constructor(private val api: DogBreedsApi) : RemoteSource {

    override suspend fun getDogBreeds(): Resource<List<DogBreed>> {
        //No need to change context to Dispatchers.IO as Retrofit handles that automatically.
        try {
            val dogBreedList = mutableListOf<DogBreed>()
            val res = api.fetchDogBreeds()
            val dogBreedNameWithSubBreedList = mutableListOf<DogBreedWithSubBreeds>()

            when (res.isSuccessful) {
                true -> {
                    res.body()?.let { body ->
                        if (body.status == DogBreedResponse.SUCCESS_STATUS) {
                            body.message.entries.forEach {
                                dogBreedNameWithSubBreedList.add(
                                    DogBreedWithSubBreeds(
                                        it.key,
                                        it.value.joinToString(COMMA) //Joining all the subbreeds by comma
                                    )
                                )
                            }
                            withContext(Dispatchers.IO) {
                                prepareDogsBreedListWithImage(
                                    this,
                                    dogBreedNameWithSubBreedList,
                                    dogBreedList
                                )
                            }
                            return Resource.Success(data = dogBreedList)
                        } else return Resource.DataError(errorCode = body.code)
                    } ?: return Resource.DataError(errorCode = res.code())
                }
                false -> return Resource.DataError(errorCode = res.code())
            }
        } catch (e: Exception) {
            Log.e("NETWORK_API_ERROR", "List cannot load ${e.hashCode()}")
            return Resource.DataError(errorCode = e.hashCode())
        }
    }

    override suspend fun getDogBreedImages(breedName: String): Resource<List<String>> {
        try {
            val res = api.fetchDogBreedImages(breedName)

            when (res.isSuccessful) {
                true -> {
                    res.body()?.let { body ->
                        if (body.status == DogBreedResponse.SUCCESS_STATUS) {
                            return Resource.Success(data = body.message)
                        } else return Resource.DataError(errorCode = body.code)
                    } ?: return Resource.DataError(errorCode = res.code())
                }
                false -> return Resource.DataError(errorCode = res.code())
            }
        } catch (e: Exception) {
            Log.e("NETWORK_API_ERROR", "Cannot get dog breed images ${e.hashCode()}")
            return Resource.DataError(errorCode = e.hashCode())
        }
    }

    private suspend fun prepareDogsBreedListWithImage(
        scope: CoroutineScope,
        dogBreedNameWithSubBreedList: List<DogBreedWithSubBreeds>,
        dogBreedList: MutableList<DogBreed>,
    ) {
        var iterator = 0
        scope.asyncAll(dogBreedNameWithSubBreedList) { api.fetchDogBreedSingleImage(it.name) }
            .awaitAll() //Awaits for completion of given deferred values without blocking a thread and
            // resumes normally with the list of values when all deferred computations are complete.
            .forEach { breedImageResponse ->
                breedImageResponse.body()?.let { breedImage ->
                    dogBreedList.add(
                        DogBreed(
                            dogBreedNameWithSubBreedList[iterator].name,
                            dogBreedNameWithSubBreedList[iterator].subBreeds,
                            breedImage.message,
                            DEFAULT_IS_FAVOURITE
                        )
                    )
                }
                iterator++
            }
    }
}
