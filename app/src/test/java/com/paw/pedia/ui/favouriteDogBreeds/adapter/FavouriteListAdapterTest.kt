package com.paw.pedia.ui.favouriteDogBreeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.paw.pedia.databinding.ListItemBinding
import com.paw.pedia.domain.model.DogBreed
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class FavouriteListAdapterTest {

    private lateinit var adapter: FavouriteListAdapter
    private lateinit var mockParent: ViewGroup
    private lateinit var mockLayoutInflater: LayoutInflater
    private lateinit var mockBinding: ListItemBinding

    @Before
    fun setup() {
        adapter = FavouriteListAdapter()
        mockParent = mock(ViewGroup::class.java)
        mockLayoutInflater = mock(LayoutInflater::class.java)
        mockBinding = mock(ListItemBinding::class.java)

        `when`(LayoutInflater.from(any())).thenReturn(mockLayoutInflater)
        `when`(mockLayoutInflater.inflate(anyInt(), any(), anyBoolean())).thenReturn(mockBinding.root)
        `when`(mockBinding.root.context).thenReturn(mock(android.content.Context::class.java))
    }

    @Test
    fun `updateAdapterData should update lists and notifyDataSetChanged`() {
        val breedList = listOf(DogBreed("Breed1", "url1","",false))
        val mockAdapter = spy(adapter) // Use a spy to allow calling real methods

        mockAdapter.updateAdapterData(breedList)

        assertEquals(breedList, mockAdapter.dogBreedList)
        assertEquals(breedList, mockAdapter.dogBreedListFiltered)
        verify(mockAdapter, times(1)).notifyDataSetChanged()
    }

    @Test
    fun `getItemCount should return size of filtered list`() {
        val breedList = listOf(DogBreed("Breed1", "url1","",false), DogBreed("Breed2", "url2","",false))
        adapter.updateAdapterData(breedList)

        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun `onBindViewHolder should bind data to views`() {
        val breed = DogBreed("Breed1", "url1","",false)
        val breedList = listOf(breed)
        adapter.updateAdapterData(breedList)
        val mockViewHolder = mock(FavouriteListAdapter.ItemViewHolder::class.java)
        `when`(mockViewHolder.binding).thenReturn(mockBinding)

        adapter.onBindViewHolder(mockViewHolder, 0)

        verify(mockBinding.dogName).text = breed.name
        verify(mockBinding).dogLogo
    }
}