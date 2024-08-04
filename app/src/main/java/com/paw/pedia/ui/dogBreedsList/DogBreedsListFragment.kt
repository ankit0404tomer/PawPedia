package com.paw.pedia.ui.dogBreedsList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.paw.pedia.databinding.FragmentDogBreedListBinding
import com.paw.pedia.domain.model.DogBreed
import com.paw.pedia.ui.BaseFragment
import com.paw.pedia.ui.dogBreedsList.adapter.BreedsListAdapter
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [DogBreedsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DogBreedsListFragment : BaseFragment(), SearchView.OnQueryTextListener{

    private val viewModel: DogBreedsListViewModel by viewModels()

    private lateinit var adapter: BreedsListAdapter
    private var _binding: FragmentDogBreedListBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDogBreedListBinding.inflate(inflater, container, false)
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
        viewModel.dogBreedsData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DogBreedsListViewModel.Result.Failure -> {
                    hideProgress()
                    showMessage(result.error)
                }

                DogBreedsListViewModel.Result.Loading -> {
                    showProgress()
                }

                is DogBreedsListViewModel.Result.Success -> {
                    hideProgress()
                    binding.apply {
                        searchView.setOnQueryTextListener(this@DogBreedsListFragment)
                        adapter = BreedsListAdapter{ item -> onItemClicked(item) }
                        recList.layoutManager = LinearLayoutManager(requireContext())
                        recList.adapter = adapter
                    }
                    adapter.updateAdapterData(result.data)
                }
            }
        }
        binding.favList.setOnClickListener {
            val action = DogBreedsListFragmentDirections.actionRandomDogFragmentToFavouriteDogListFragment()
            findNavController().navigate(action)
        }
    }


    private fun onItemClicked(breed: DogBreed) {
        val action = DogBreedsListFragmentDirections.actionRandomDogFragmentToBreedDetailFragment(breed.name)
        findNavController().navigate(action)
    }

    override fun onQueryTextSubmit(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }
}