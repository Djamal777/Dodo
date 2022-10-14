package com.example.dodo.presentation.menu_fragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dodo.R
import com.example.dodo.databinding.CategoryItemBinding
import com.example.dodo.domain.model.Category
import javax.inject.Inject

class CategoriesAdapter @Inject constructor(
    private val c: Context
):RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    var selectedItemPos = 0
    var lastItemSelectedPos = 0

    inner class CategoryViewHolder(private val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(bindingAdapterPosition)
                }
            }
        }
        fun bind(item:Category){
            binding.category.text=item.strCategory
        }
        fun selected() {
            binding.root.setBackgroundColor(c.resources.getColor(R.color.pink_light))
            binding.category.setTextColor(c.resources.getColor(R.color.pink))
        }
        fun default() {
            binding.root.setBackgroundColor(c.resources.getColor(R.color.white))
            binding.category.setTextColor(c.resources.getColor(R.color.grey_text))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item=differ.currentList[position]
        if(position == selectedItemPos)
            holder.selected()
        else
            holder.default()
        holder.bind(item)
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback=object: DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.strCategory==newItem.strCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem==newItem
        }
    }

    val differ= AsyncListDiffer(this,differCallback)

    private var onItemClickListener:((Int)->Unit)?=null

    fun setOnItemClickListener(listener:(Int)->Unit){
        onItemClickListener=listener
    }
}