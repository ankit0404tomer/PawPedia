package com.paw.pedia.ui.dogBreedDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.paw.pedia.domain.model.DogBreed
import com.paw.pedia.domain.usecase.dogBreeds.DogBreedsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DogBreedDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // To handle LiveData in tests

    private lateinit var viewModel: DogBreedsListViewModel
    private val dogBreedsUseCase: DogBreedsUseCase = mockk()
    private val observer: Observer<DogBreedsListViewModel.Result> = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined) // Set the main dispatcher for coroutines
        viewModel = DogBreedsListViewModel(dogBreedsUseCase)
        viewModel.dogBreedsData.observeForever(observer)
    }

    @Test
    fun `test getDogBreedsList success`() = runTest {
        // Prepare test data
        val dogBreeds = listOf(
            DogBreed(
                name = "poodle",
                subBreeds = "",
                isFavourite = false,
                imageUrl = "dummy_url"
            ), DogBreed(
                name = "akita",
                subBreeds = "",
                isFavourite = false,
                imageUrl = "dummy_url"
            )
        )
        val flow: Flow<List<DogBreed>> = flowOf(dogBreeds)
        coEvery { dogBreedsUseCase.getDogBreeds() } returns flow

        // Trigger the method
        viewModel.getDogBreedsList()

        // Verify that LiveData is updated correctly
        val expectedResult = DogBreedsListViewModel.Result.Success(dogBreeds)
        assertEquals(expectedResult, viewModel.dogBreedsData.value)
        verify { observer.onChanged(expectedResult) }
    }

    @Test
    fun `test getDogBreedsList failure`() = runTest {
        // Prepare test data
        val errorMessage = "Error occurred"
        val flow: Flow<List<DogBreed>> = flowOf()
        coEvery { dogBreedsUseCase.getDogBreeds() } returns flow

        // Trigger the method
        viewModel.getDogBreedsList()

        // Verify that LiveData is updated correctly
        val expectedResult = DogBreedsListViewModel.Result.Failure(errorMessage)
        assertEquals(expectedResult, viewModel.dogBreedsData.value)
        verify { observer.onChanged(expectedResult) }
    }

}