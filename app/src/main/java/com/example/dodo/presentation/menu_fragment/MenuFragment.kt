package com.example.dodo.presentation.menu_fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.dodo.R
import com.example.dodo.common.Resource
import com.example.dodo.databinding.FragmentMenuBinding
import com.example.dodo.presentation.menu_fragment.adapters.BannersAdapter
import com.example.dodo.presentation.menu_fragment.adapters.CategoriesAdapter
import com.example.dodo.presentation.menu_fragment.adapters.MealsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private val viewModel: MenuViewModel by viewModels()
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var mealsAdapter: MealsAdapter
    private lateinit var bannersAdapter: BannersAdapter
    private lateinit var verticalLinearLayoutManager: LinearLayoutManager
    private lateinit var horizontalLinearLayoutManager: LinearLayoutManager
    private lateinit var horizontalSmoothScroller: SmoothScroller

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.cities,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerLocation.adapter = adapter
        }
        bannersAdapter = BannersAdapter(requireContext())
        categoriesAdapter = CategoriesAdapter(requireContext())
        mealsAdapter = MealsAdapter()
        verticalLinearLayoutManager = LinearLayoutManager(requireContext())
        horizontalLinearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.apply {
            recyclerViewBanners.adapter = bannersAdapter
            recyclerViewBanners.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            recyclerViewCategories.adapter = categoriesAdapter
            recyclerViewCategories.layoutManager = horizontalLinearLayoutManager
            (recyclerViewCategories.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                false

            recyclerViewMenu.adapter = mealsAdapter
            recyclerViewMenu.layoutManager = verticalLinearLayoutManager
            recyclerViewMenu.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    scrollCategory()
                }
            })
        }


        viewModel.categories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.isNotEmpty() == true) {
                        viewModel.currentCategory = it.data[0]
                        categoriesAdapter.differ.submitList(it.data)
                        binding.recyclerViewCategories.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.errorImage.visibility = View.VISIBLE
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    it.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        viewModel.meals.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data?.isNotEmpty() == true) {
                        mealsAdapter.differ.submitList(it.data)
                        binding.recyclerViewMenu.visibility = View.VISIBLE
                        setCategoriesOnClickListener()
                    } else {
                        binding.errorImage.visibility = View.VISIBLE
                    }
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                    it.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setCategoriesOnClickListener() {
        horizontalSmoothScroller =
            object : LinearSmoothScroller(context) {
                override fun getHorizontalSnapPreference(): Int {
                    return SNAP_TO_START
                }

                override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
                    return 0.4f
                }
            }
        categoriesAdapter.apply {
            setOnItemClickListener {
                selectedItemPos = it
                notifyItemChanged(lastItemSelectedPos)
                lastItemSelectedPos = selectedItemPos
                notifyItemChanged(selectedItemPos)
                val category = differ.currentList[it]
                val index = mealsAdapter.differ.currentList.indexOfFirst { meal ->
                    meal.category == category.strCategory
                }
                horizontalSmoothScroller.targetPosition = it
                binding.appBar.setExpanded(false)
                verticalLinearLayoutManager.scrollToPositionWithOffset(index, 0)
                horizontalLinearLayoutManager.startSmoothScroll(horizontalSmoothScroller)
            }
        }
    }

    private fun scrollCategory(){
        val firstIndex=verticalLinearLayoutManager.findFirstVisibleItemPosition()
        Log.d("TAG", "scrollCategory: $firstIndex")
        if (mealsAdapter.differ.currentList[firstIndex].category != viewModel.currentCategory.strCategory) {
            categoriesAdapter.apply {
                val index=differ.currentList.indexOfFirst {
                    it.strCategory==mealsAdapter.differ.currentList[firstIndex].category
                }
                Log.d("TAG", "scrollCategory: ${categoriesAdapter.differ.currentList[index]}")
                selectedItemPos = index
                notifyItemChanged(lastItemSelectedPos)
                lastItemSelectedPos = selectedItemPos
                notifyItemChanged(selectedItemPos)
                horizontalSmoothScroller.targetPosition=index
                horizontalLinearLayoutManager.startSmoothScroll(horizontalSmoothScroller)
                viewModel.currentCategory=categoriesAdapter.differ.currentList[index]
            }
        }
    }
}