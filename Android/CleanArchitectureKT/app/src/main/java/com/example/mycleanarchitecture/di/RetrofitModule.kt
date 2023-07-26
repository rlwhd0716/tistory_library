package kr.or.koelsa.elsm.di

import android.util.Log
import com.google.gson.GsonBuilder
import kr.or.koelsa.elsm.BuildConfig
import kr.or.koelsa.elsm.data.api.ApiService
import kr.or.koelsa.elsm.data.api.ElevatorCorpLinkApiService
import kr.or.koelsa.elsm.data.api.NaverApiService
import kr.or.koelsa.elsm.domain.util.pref.PrefObj.LOGIN_ACCESS_TOKEN
import kr.or.koelsa.elsm.domain.util.pref.PrefObj.PUSH_TOKEN
import kr.or.koelsa.elsm.util.Const
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
        Log.w("Access-Token", "Bearer ${LOGIN_ACCESS_TOKEN ?: ""}")
        Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            logger.debug("chain - $original")
            chain.proceed(original.newBuilder().apply {
                addHeader("accept", "*/*")
                addHeader("Content-Type", "application/json")
                addHeader("Connection", "close")
                addHeader("FCM-Token",PUSH_TOKEN ?: "")
                addHeader("Authorization","Bearer ${LOGIN_ACCESS_TOKEN ?: ""}")
                addHeader("X-NCP-APIGW-API-KEY-ID", Const.NAVER_API_ID)
                addHeader("X-NCP-APIGW-API-KEY", Const.NAVER_API_SECRET)
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
            .baseUrl(BuildConfig.API_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build().create(ApiService::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.NAVER_API_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build().create(NaverApiService::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.ELEV_LINK_API_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build().create(ElevatorCorpLinkApiService::class.java)
    }
}