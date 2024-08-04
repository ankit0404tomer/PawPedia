package com.paw.pedia.ui.dogBreedsList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paw.pedia.domain.model.DogBreed
import com.paw.pedia.domain.usecase.dogBreeds.DogBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogBreedsListViewModel @Inject constructor(
    private val dogBreedsUseCase: DogBreedsUseCase
) : ViewModel() {

    private val _dogBreedsData = MutableLiveData<Result>()
    val dogBreedsData = _dogBreedsData

    init {
        getDogBreedsList()
    }

    fun getDogBreedsList() {
        // CoroutineScope tied to this ViewModel.
        // This scope will be canceled when ViewModel will be cleared, i.e ViewModel.onCleared is called
        viewModelScope.launch {
            dogBreedsUseCase.getDogBreeds().collect { result ->
                _dogBreedsData.value= Result.Success(result)
            }
        }
    }

    sealed class Result {
        data object Loading : Result()
        class Success(val data: List<DogBreed>) : Result()
        class Failure(val error: String) : Result()
    }
}