package com.example.cleanarchitecturekoin.di

import com.example.data.api.ApiService
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val retrofitModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        GsonConverterFactory.create(GsonBuilder().setLenient().create())
    }

    single {
//        Log.w("Access-Token", "Bearer ${LOGIN_ACCESS_TOKEN ?: ""}")
        Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            logger.debug("chain - $original")
            chain.proceed(original.newBuilder().apply {
                addHeader("accept", "*/*")
                addHeader("Content-Type", "application/json")
                addHeader("Connection", "close")
//                addHeader("Authorization","Bearer ${LOGIN_ACCESS_TOKEN ?: ""}")
            }.build())
        }
    }

    single {
        OkHttpClient.Builder().apply {
            addInterceptor(get<Interceptor>())
            addInterceptor(get<HttpLoggingInterceptor>())
            retryOnConnectionFailure(false)
            cache(null)
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(15, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("URL")
            .client(get<OkHttpClient>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build().create(ApiService::class.java)
    }
}