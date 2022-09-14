package com.example.di

import android.content.SharedPreferences
import com.example.myapp.data.remote.ProfileApi
import com.example.myapp.domain.repository.ProfileRepository
import com.example.myapp.data.repository.ProfileRepositoryImpl
import com.example.myapp.domain.use_case.GetProfileUseCase
import com.example.myapp.domain.use_case.LogoutUseCase
import com.example.myapp.domain.use_case.ProfileUseCases
import com.example.myapp.domain.use_case.UpdateProfileUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi {
        return Retrofit.Builder()
            .baseUrl(ProfileApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: ProfileApi, gson: Gson, sharedPreferences: SharedPreferences): ProfileRepository {
        return  ProfileRepositoryImpl(api, gson, sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases {
        return ProfileUseCases(
                getProfile = GetProfileUseCase(repository),
                updateProfile = UpdateProfileUseCase(repository),
                logout = LogoutUseCase(repository)
        )
    }

}