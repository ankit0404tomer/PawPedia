package com.paw.pedia.ui.dogBreedsList.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.paw.pedia.R
import com.paw.pedia.databinding.ListItemBinding
import com.paw.pedia.domain.model.DogBreed
import com.paw.pedia.utils.extensions.capitalizeFirstLetter


class BreedsListAdapter(private val onItemClick: (DogBreed) -> Unit) : RecyclerView.Adapter<BreedsListAdapter.ItemViewHolder>(), Filterable {

    private var dogBreedList: List<DogBreed> = ArrayList()
    private var dogBreedListFiltered: List<DogBreed> = ArrayList()

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
            dogName.text = breed.name.capitalizeFirstLetter()
            val defaultOptions: RequestOptions = RequestOptions().error(R.drawable.placeholder_dog)
            Glide.with(dogLogo.context).setDefaultRequestOptions(defaultOptions)
                .load(breed.imageUrl).into(dogLogo)
        }
        holder.itemView.setOnClickListener { onItemClick(breed) }
    }

    inner class ItemViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                dogBreedListFiltered = if (charString.isEmpty()) dogBreedList else {
                    val filteredList = ArrayList<DogBreed>()
                    dogBreedList.filter {
                        (it.name.startsWith(constraint!!, true))
                    }.forEach { filteredList.add(it) }
                    filteredList
                }
                return FilterResults().apply { values = dogBreedListFiltered }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dogBreedListFiltered =
                    if (results?.values == null) ArrayList() else results.values as List<DogBreed>
                notifyDataSetChanged()
            }
        }
    }
}