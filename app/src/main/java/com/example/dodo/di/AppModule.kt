package com.example.dodo.di

import android.app.Application
import androidx.room.Room
import com.example.dodo.common.Constants.BASE_URL
import com.example.dodo.data.local.MealDao
import com.example.dodo.data.local.MealDatabase
import com.example.dodo.data.remote.TheMealdbApi
import com.example.dodo.data.repository.MealRepositoryImpl
import com.example.dodo.domain.model.Meal
import com.example.dodo.domain.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApi():TheMealdbApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMealdbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(app:Application)= Room.databaseBuilder(app,MealDatabase::class.java, "meal_database").build()

    @Provides
    @Singleton
    fun provideDao(db:MealDatabase):MealDao = db.mealDao()

    @Provides
    @Singleton
    fun provideRepository(api:TheMealdbApi, dao:MealDao):MealRepository{
        return MealRepositoryImpl(api, dao)
    }
}