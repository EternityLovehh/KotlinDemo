package com.meditrust.module_base.component

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.meditrust.module_base.module.AppModule
import dagger.Component
import okhttp3.Cookie
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/4
 * @desc:
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun retrofit(): Retrofit

    fun okHttpClient(): OkHttpClient

    fun persistentCookieJar(): PersistentCookieJar

    fun cookies(): List<Cookie>
}