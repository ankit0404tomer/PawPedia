package com.paw.pedia.ui.favouriteDogBreeds.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.paw.pedia.R
import com.paw.pedia.databinding.ListItemBinding
import com.paw.pedia.domain.model.DogBreed


class FavouriteListAdapter : RecyclerView.Adapter<FavouriteListAdapter.ItemViewHolder>() {

    internal var dogBreedList: List<DogBreed> = ArrayList()
    internal var dogBreedListFiltered: List<DogBreed> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapterData(breedList: List<DogBreed>) {
        dogBreedList = breedList
        dogBreedListFiltered = dogBreedList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dogBreedListFiltered.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val breed = dogBreedListFiltered[position]
        holder.binding.apply {
            dogName.text = breed.name
            val defaultOptions: RequestOptions = RequestOptions().error(R.drawable.placeholder_dog)
            Glide.with(dogLogo.context).setDefaultRequestOptions(defaultOptions)
                .load(breed.imageUrl).into(dogLogo)
        }
    }

    inner class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

}