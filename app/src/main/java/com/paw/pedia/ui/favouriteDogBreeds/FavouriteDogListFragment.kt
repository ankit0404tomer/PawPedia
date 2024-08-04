package com.paw.pedia.ui.favouriteDogBreeds

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paw.pedia.R
import com.paw.pedia.databinding.FragmentDogBreedListBinding
import com.paw.pedia.databinding.FragmentFavouriteDogListBinding
import com.paw.pedia.ui.BaseFragment
import com.paw.pedia.ui.dogBreedsList.DogBreedsListViewModel
import com.paw.pedia.ui.dogBreedsList.adapter.BreedsListAdapter
import com.paw.pedia.ui.favouriteDogBreeds.adapter.FavouriteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteDogListFragment : BaseFragment(){

    private val viewModel: FavouriteDogBreedsViewModel by viewModels()

    private lateinit var adapter: FavouriteListAdapter
    private var _binding: FragmentFavouriteDogListBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteDogListBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        Log.d("TAG", "onViewCreated")
    }
    private fun initObserver() {
        viewModel.favouriteDogBreedsData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is FavouriteDogBreedsViewModel.Result.Failure -> {
                    hideProgress()
                    showMessage(result.error)
                }

                FavouriteDogBreedsViewModel.Result.Loading -> {
                    showProgress()
                }

                is FavouriteDogBreedsViewModel.Result.Success -> {
                    hideProgress()
                    binding.apply {
                        adapter = FavouriteListAdapter()
                        recList.layoutManager = LinearLayoutManager(requireContext())
                        recList.adapter = adapter
                    }
                    adapter.updateAdapterData(result.data)
                }
            }
        }
    }
}