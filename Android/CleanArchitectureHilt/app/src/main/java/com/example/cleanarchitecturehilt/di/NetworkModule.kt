package com.example.cleanarchitecturehilt.di

import android.annotation.SuppressLint
import android.content.Context
import com.example.cleanarchitecturehilt.data.api.ApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.example.cleanarchitecturehilt.BuildConfig
import com.example.cleanarchitecturehilt.lib.ext.logE
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.*


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor =
        Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            chain.proceed(original.newBuilder().apply {
                addHeader("accept", "*/*")
                addHeader("Content-Type", "application/json")
                addHeader("Connection", "close")
            }.build())
        }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(httpLoggingInterceptor)
        .retryOnConnectionFailure(true)
        .cache(null)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson().newBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(@ApplicationContext appContext: Context, okHttpClient: OkHttpClient, gson: Gson) : Retrofit.Builder {
        val sslClient = getPinnedCertSslSocketFactory(appContext)
        return Retrofit.Builder().apply {
            if (sslClient == null) {
                this.client(okHttpClient)
            } else {
                val trustManager = TrustEveryoneManager()
                val okHttpsClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslClient, trustManager)
                    .build()
                this.client(okHttpsClient)
            }
        }.addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit.Builder): ApiService {
        return retrofit
            .baseUrl("")
            .build()
            .create(ApiService::class.java)
    }
}

fun getPinnedCertSslSocketFactory(applicationContext: Context): SSLSocketFactory? {
//    try {
//        val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
//        val caInput: InputStream = applicationContext.resources.openRawResource(R.raw.my_cert)
//        var ca: Certificate? = null
//        try {
//            ca = cf.generateCertificate(caInput)
//            println("ca=" + (ca as X509Certificate?)!!.subjectDN)
//        } catch (e: CertificateException) {
//            e.printStackTrace()
//        } finally {
//            caInput.close()
//        }
//        if (ca == null) {
//            return null
//        }
//
//        val keyStoreType: String = KeyStore.getDefaultType()
//        val keyStore: KeyStore = KeyStore.getInstance(keyStoreType)
//        keyStore.load(null, null)
//        keyStore.setCertificateEntry("ca", ca)
//
//        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
//        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
//        tmf.init(keyStore)
//
//        val sslContext = SSLContext.getInstance("TLS")
//        sslContext.init(null, tmf.trustManagers, null)
//        return sslContext.socketFactory
//    } catch (e: NoSuchAlgorithmException) {
//        Timber.e("getSSLCert - NoSuchAlgorithmException")
//    } catch (e: IOException) {
//        Timber.e("getSSLCert - IOException")
//    } catch (e: KeyStoreException) {
//        Timber.e("getSSLCert - KeyStoreException")
//    } catch (e: KeyManagementException) {
//        Timber.e("getSSLCert - KeyManagementException")
//    } catch (e: Exception) {
//        Timber.e("getSSLCert - Exception")
//    }
    return null
}

@SuppressLint("CustomX509TrustManager")
internal class TrustEveryoneManager : X509TrustManager {
    @Throws(CertificateException::class)
    override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
        logE("chain::: $chain")
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
        logE("chain::: $chain")
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return arrayOf()
    }
}
