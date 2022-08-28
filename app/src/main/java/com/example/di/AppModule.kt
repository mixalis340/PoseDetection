package com.example.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.myapp.Constants
import com.example.myapp.domain.use_case.GetOwnUserIdUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {

        @Provides
        @Singleton
        fun provideSharedPref(app : Application): SharedPreferences {
            return app.getSharedPreferences(
                Constants.SHARED_PREF_NAME,
                MODE_PRIVATE
            )
        }

        /*
        @Provides
        @Singleton
        fun provideJwtToken(sharedPreferences: SharedPreferences): String {
            return sharedPreferences.getString(Constants.KEY_JWT_TOKEN,"") ?: ""
        }*/

        @Provides
        @Singleton
        fun provideOkHttpClient(sharedPreferences: SharedPreferences) : OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor{
                    val token = sharedPreferences.getString(Constants.KEY_JWT_TOKEN,"")
                    val modifierRequest = it.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    it.proceed(modifierRequest)
                }
                .build()
        }

        @Provides
        @Singleton
        fun provideGson(): Gson {
            return Gson()
        }

        @Provides
        @Singleton
        fun provideGetOwnUserIdUseCase(sharedPreferences: SharedPreferences): GetOwnUserIdUseCase {
            return GetOwnUserIdUseCase(sharedPreferences)
        }
    }
