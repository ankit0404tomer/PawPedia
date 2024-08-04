package com.paw.pedia.ui.dogBreedDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.pedia.data.Resource
import com.paw.pedia.domain.usecase.dogBreedImages.DogBreedImagesUseCase
import com.paw.pedia.domain.usecase.favouriteDogBreeds.FavouriteDogBreedsUseCase
import com.paw.pedia.ui.dogBreedsList.DogBreedsListViewModel.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogBreedDetailsViewModel @Inject constructor(
    private val dogBreedImagesUseCase: DogBreedImagesUseCase,
    private val favouriteDogBreedsUseCase: FavouriteDogBreedsUseCase
) : ViewModel() {


    private val _dogBreedsData = MutableLiveData<Result>()
    val dogBreedsData = _dogBreedsData

    fun getDogBreedImages(name: String): MutableLiveData<Result> {
        // CoroutineScope tied to this ViewModel.
        // This scope will be canceled when ViewModel will be cleared, i.e ViewModel.onCleared is called
        viewModelScope.launch {
            dogBreedImagesUseCase.getDogBreedImages(name).collect { result ->
                handleDogBreedImagesResponse(result)
            }
        }
        return dogBreedsData
    }

    private fun handleDogBreedImagesResponse(result: Resource<List<String>>) {
        _dogBreedsData.value= result.data?.let { Result.Success(it) }
    }

    fun addToFavourites(dogName: String, isFavourite: Boolean) {
        viewModelScope.launch {
            favouriteDogBreedsUseCase.addToFavourites(name = dogName, isFavourite = isFavourite)
        }
    }

    sealed class Result {
        data object Loading : Result()
        class Success(val data: List<String>) : Result()
        class Failure(val error: String) : Result()
    }
}