package com.example.webviewexam.di

import android.annotation.SuppressLint
import android.content.Context
import com.example.webviewexam.BuildConfig
import com.example.webviewexam.data.ApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideInterceptor() : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .retryOnConnectionFailure(true)
        .cache(null)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideGson() : Gson = Gson().newBuilder()
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext appContext: Context, okHttpClient: OkHttpClient, gson: Gson) : Retrofit {
        val sslClient = getPinnedCertSslSocketFactory(appContext)
        if (sslClient == null) {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        } else {
            val trustManager = TrustEveryoneManager()
            val okHttpsClient = okHttpClient.newBuilder()
                .sslSocketFactory(sslClient, trustManager)
                .build()
            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpsClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}

fun getPinnedCertSslSocketFactory(applicationContext: Context): SSLSocketFactory? {
    return null
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
//    return null
}

@SuppressLint("CustomX509TrustManager")
internal class TrustEveryoneManager : X509TrustManager {
    @Throws(CertificateException::class)
    override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
        Timber.e(chain.toString())
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
        Timber.e(chain.toString())
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return arrayOf()
    }
}