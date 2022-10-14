package com.example.dodo.presentation.menu_fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dodo.databinding.FoodItemBinding
import com.example.dodo.domain.model.Meal
import javax.inject.Inject

class MealsAdapter @Inject constructor(): RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {

    inner class MealViewHolder(private val binding: FoodItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Meal){
            binding.apply{
                Glide.with(itemView).load(item.strMealThumb).into(binding.foodImage)
                name.text=item.strMeal
                description.text=item.strMeal
                buy.text=item.price
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsAdapter.MealViewHolder {
        return MealViewHolder(
            FoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MealsAdapter.MealViewHolder, position: Int) {
        val item=differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback=object: DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }
    }

    val differ= AsyncListDiffer(this,differCallback)
}