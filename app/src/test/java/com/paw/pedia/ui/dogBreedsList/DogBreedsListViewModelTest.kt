package com.paw.pedia.ui.dogBreedsList

import com.paw.pedia.domain.model.DogBreed
import com.paw.pedia.utils.CoroutineTestRule
import com.paw.pedia.domain.usecase.dogBreeds.DogBreedsUseCase
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class DogBreedsListViewModelTest {

    private lateinit var viewModel: DogBreedsListViewModel

    private val dogBreedsUseCase = mockk<DogBreedsUseCase>()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        // empty body as view model should be initialized with mocking applied
    }

    @Test
    fun `initialize then fetch dog breeds succeeded`() = runTest {
        // given
        val breed = DogBreed(
            name = "poodle",
            subBreeds = "",
            isFavourite = false,
            imageUrl = "dummy_url"
        )

        every { dogBreedsUseCase.getDogBreeds() } returns flow {
            emit(listOf(breed, breed))
        }

        // when
        // view model is initialized
        viewModel = DogBreedsListViewModel(dogBreedsUseCase)

        // then
        val expectedResult = DogBreedsListViewModel.Resul
        assertEquals(expectedResult, viewModel.dogBreedsData.value)
        verify { observer.onChanged(expectedResult) }
    }

    @Test
    fun `initialize then fetch dog breeds failed`() = runTest {
        // given
        every { dogBreedsUseCase.getDogBreeds() } returns flow {
            emit(emptyList())
        }

        // when
        // view model is initialized
        viewModel = DogBreedsListViewModel(dogBreedsUseCase)

        // then
        viewModel.uiState.dogBreeds?.size shouldBe 0
        viewModel.uiState.isLoading shouldBe false
    }
}