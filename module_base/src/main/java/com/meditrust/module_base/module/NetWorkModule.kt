package com.meditrust.module_base.module

import android.util.Log
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.GsonBuilder
import com.meditrust.module_base.BuildConfig
import com.meditrust.module_base.MyApplication
import com.meditrust.module_base.utils.SpUtils
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Utf8
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URLDecoder
import javax.inject.Singleton

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/4
 * @desc:
 */
@Module
class NetWorkModule {

    private val TAG = "NetWorkModule"

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()))
            .build()


    @Provides
    @Singleton
    internal fun provideOkhttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor, headerInterceptor: Interceptor,
        persistentCookieJar: PersistentCookieJar
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .cookieJar(persistentCookieJar)
            .build()

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                val text = URLDecoder.decode(message, Utf8.toString())
                Log.e(TAG, text)
            }

        })

    @Provides
    @Singleton
    internal fun provideHeaderInterceptor(): Interceptor =
        Interceptor.invoke {
            val token = SpUtils.getString(SpUtils.SP_KEYS.APP_TOKEN)
            val headerBuilders = it.request().headers.newBuilder()
            headerBuilders.add("Accept", "*/*")
            token?.let { headerBuilders.add("appToken", token) }
            val moreHeaders = headerBuilders.build()
            val requestBuilder = it.request().newBuilder()
                .headers(moreHeaders)
                .build()
            return@invoke it.proceed(requestBuilder)
        }


    @Provides
    @Singleton
    internal fun providePersistentCookieJar(sharedPrefsCookiePersistor: SharedPrefsCookiePersistor): PersistentCookieJar {
        return PersistentCookieJar(SetCookieCache(), sharedPrefsCookiePersistor)
    }

    @Provides
    @Singleton
    internal fun provideSharedPrefsCookiePersistor(): SharedPrefsCookiePersistor =
        SharedPrefsCookiePersistor(MyApplication.instance)

    @Provides
    @Singleton
    internal fun provideCookies(sharedPrefsCookiePersistor: SharedPrefsCookiePersistor) =
        sharedPrefsCookiePersistor.loadAll()
}