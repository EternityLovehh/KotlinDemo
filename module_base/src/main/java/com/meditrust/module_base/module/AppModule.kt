package com.meditrust.module_base.module

import android.app.Application
import dagger.Module
import dagger.Provides

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/4
 * @desc:
 */
@Module(includes = [NetWorkModule::class])
class AppModule(private val application: Application) {
    @Provides
    internal fun provideApplication(): Application = application
}