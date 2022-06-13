package com.xl.simplemvvm.net

import com.xl.xl_base.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideUnsplashService(): WanApi {
        return  ApiService("https://www.wanandroid.com").retrofit.create(WanApi::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideUnsplashService(): WanApi {
//        return  ApiService("http://baobab.kaiyanapp.com/api/").retrofit.create(WanApi::class.java)
//    }
}