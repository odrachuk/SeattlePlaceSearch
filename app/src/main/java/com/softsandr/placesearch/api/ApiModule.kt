package com.softsandr.placesearch.api

import com.google.gson.GsonBuilder
import com.softsandr.placesearch.BuildConfig
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.CookieJar
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import javax.inject.Singleton

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideCookieJar() : CookieJar {
        val cookieManager = CookieManager()
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
        return JavaNetCookieJar(cookieManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cookieJar: CookieJar): OkHttpClient {
        val builder = OkHttpClient.Builder().cookieJar(cookieJar)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return builder.build()
    }

    @Provides
    fun provideApiClient(okHttpClient: OkHttpClient): ApiClient {
        return Retrofit.Builder()
            .baseUrl(ApiClient.API_CLIENT_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
            .create(ApiClient::class.java)
    }
}