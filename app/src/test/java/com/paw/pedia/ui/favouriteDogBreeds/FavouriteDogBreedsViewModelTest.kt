package com.paw.pedia.ui.favouriteDogBreeds

import com.paw.pedia.domain.model.DogBreed
import com.paw.pedia.domain.usecase.favouriteDogBreeds.FavouriteDogBreedsUseCase
import com.paw.pedia.utils.CoroutineTestRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavouriteDogBreedsViewModelTest {

    private lateinit var viewModel: FavouriteDogBreedsViewModel

    private val favouriteDogBreedsUseCase = mockk<FavouriteDogBreedsUseCase>()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        // empty body as view model should be initialized with mocking applied
    }

    @Test
    fun `initialize then fetch favourite dog breeds`() = runTest {
        // given
        val breed = DogBreed(
            name = "poodle",
            subBreeds = "",
            isFavourite = true,
            imageUrl = "dummy_url"
        )

        every { favouriteDogBreedsUseCase.getFavouriteDogBreeds() } returns flow {
            emit(listOf(breed, breed))
        }

        // when
        // view model is initialized
        viewModel = FavouriteDogBreedsViewModel(favouriteDogBreedsUseCase)

        // then
//        viewModel.favouriteDogBreedsData.value.data.size shouldBe 2
//        viewModel.uiState.isLoading shouldBe false
    }

    @Test
    fun `initialize then fetch favourite dog breeds which has no items`() = runTest {
        // given
        every { favouriteDogBreedsUseCase.getFavouriteDogBreeds() } returns flow {
          //  emit(emptyList())
        }

        // when
        // view model is initialized
        viewModel = FavouriteDogBreedsViewModel(favouriteDogBreedsUseCase)

        // then
//        viewModel.uiState.dogBreeds?.size shouldBe 0
//        viewModel.uiState.isLoading shouldBe false
    }
}