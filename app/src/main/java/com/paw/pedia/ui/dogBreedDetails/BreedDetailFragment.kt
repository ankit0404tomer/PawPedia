package com.paw.pedia.ui.dogBreedDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.paw.pedia.R
import com.paw.pedia.databinding.FragmentBreedDetailBinding
import com.paw.pedia.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BreedDetailFragment : BaseFragment() {
    private val viewModel: DogBreedDetailsViewModel by viewModels()

    private var _binding: FragmentBreedDetailBinding? = null
    lateinit var breed:String
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBreedDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        breed = BreedDetailFragmentArgs.fromBundle(requireArguments()).breed
        initObserver(breed)
        Log.d("TAG", "onViewCreated")
    }

    private fun initObserver(breed: String) {
        viewModel.getDogBreedImages(breed).observe(viewLifecycleOwner) { result ->
            when (result) {
                is DogBreedDetailsViewModel.Result.Failure -> {
                    hideProgress()
                    showMessage(result.error)
                }

                DogBreedDetailsViewModel.Result.Loading -> {
                    showProgress()
                }

                is DogBreedDetailsViewModel.Result.Success -> {
                    hideProgress()
                    initUI(result.data)
                }
            }
        }
    }


    private fun initUI(data: List<String>) {
        binding.apply {
            val defaultOptions: RequestOptions = RequestOptions().error(R.drawable.placeholder_dog)
            Glide.with(imgDog.context).setDefaultRequestOptions(defaultOptions)
                .load(data[0]).into(imgDog)

            imgFav.setOnClickListener {
                viewModel.addToFavourites(breed, true)
                Toast.makeText(activity, "Added to favourites", Toast.LENGTH_SHORT).show()
            }
        }

    }

}