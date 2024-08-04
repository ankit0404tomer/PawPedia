package com.paw.pedia.ui.favouriteDogBreeds

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.pedia.domain.model.DogBreed
import com.paw.pedia.domain.usecase.favouriteDogBreeds.FavouriteDogBreedsUseCase
import com.paw.pedia.ui.dogBreedsList.DogBreedsListViewModel.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteDogBreedsViewModel @Inject constructor(
    private val dogBreedsUseCase: FavouriteDogBreedsUseCase
) : ViewModel() {
    private val _favouriteDogBreedsData = MutableLiveData<Result>()
    val favouriteDogBreedsData = _favouriteDogBreedsData

    init {
        getFavouriteDogBreedsList()
    }

    private fun getFavouriteDogBreedsList() {
        viewModelScope.launch {
            dogBreedsUseCase.getFavouriteDogBreeds().collect { result ->
                _favouriteDogBreedsData.value= Result.Success(result)
            }
        }
    }

    sealed class Result {
        data object Loading : Result()
        class Success(val data: List<DogBreed>) : Result()
        class Failure(val error: String) : Result()
    }
}