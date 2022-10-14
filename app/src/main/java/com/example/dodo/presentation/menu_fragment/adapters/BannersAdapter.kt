package com.example.dodo.presentation.menu_fragment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.dodo.R
import com.example.dodo.databinding.BannerItemBinding
import javax.inject.Inject

class BannersAdapter @Inject constructor(
    private val c: Context,
):RecyclerView.Adapter<BannersAdapter.BannerViewHolder>(){

    private val bannerItems:List<Int> = listOf(R.drawable.img, R.drawable.img_1, R.drawable.img_2)

    inner class BannerViewHolder(private val binding:BannerItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:Int){
            binding.banner.setImageDrawable(AppCompatResources.getDrawable(c,item))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(BannerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val item=bannerItems[position]
        holder.bind(item)
    }

    override fun getItemCount() = bannerItems.size
}