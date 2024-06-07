package com.example.easyfoodapp.activites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.easyfoodapp.R
import com.example.easyfoodapp.databinding.ActivityMealBinding
import com.example.easyfoodapp.fragments.HomeFragment
import com.example.easyfoodapp.pojo.Meal
import com.example.easyfoodapp.viewmodel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMVVM: MealViewModel
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtbLink: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMVVM = ViewModelProviders.of(this)[MealViewModel::class.java]

        getMealIformationFromIntent()
        setInformationInViews()
        mealMVVM.getMealDetail(mealId)
        observerMealDeatialsLiveData()
        loadingCase()

        onYoutubeImageClick()
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtbLink))
            startActivity(intent)
        }
    }

    private fun observerMealDeatialsLiveData() {
        mealMVVM.observeMealDetailsLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(value: Meal) {
                val meal = value
                onResponseCase()
                binding.tvCategory.text = "Category : ${meal.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvInstructionsStep.text = meal.strInstructions
                youtbLink = meal.strYoutube
            }
        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealDetail)
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getMealIformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun loadingCase() {

        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE


    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }
}