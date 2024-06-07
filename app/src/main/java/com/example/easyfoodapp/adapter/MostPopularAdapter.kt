package com.example.easyfoodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easyfoodapp.databinding.PopularItemsBinding
import com.example.easyfoodapp.pojo.CategoryMeals

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick: ((CategoryMeals) -> Unit)
    private var mealsList = ArrayList<CategoryMeals>()
    fun setMeals(mealsList: ArrayList<CategoryMeals>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    class PopularMealViewHolder(var binding: PopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(
            PopularItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }
}