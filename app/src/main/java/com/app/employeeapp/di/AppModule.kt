package com.app.employeeapp.di

import com.app.employeeapp.commonutil.Constants
import com.app.employeeapp.data.ApiService
import com.app.employeeapp.repository.EmployeeRepository
import com.app.employeeapp.repository.EmployeeRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

    @Singleton
    @Provides
    fun provideRetrofitInstance(gsonCreator: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonCreator))
    }

    @Singleton
    @Provides
    fun provideGsonCreator(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofitInstance: Retrofit.Builder): ApiService {
        return retrofitInstance
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideEmployeeRepository(
        apiService: ApiService
    ) = EmployeeRepositoryImpl(apiService) as EmployeeRepository
}