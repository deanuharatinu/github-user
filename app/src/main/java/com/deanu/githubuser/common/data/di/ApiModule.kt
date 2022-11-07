package com.deanu.githubuser.common.data.di

import com.deanu.githubuser.BuildConfig
import com.deanu.githubuser.common.data.api.ApiConstants
import com.deanu.githubuser.common.data.api.GithubUserApi
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
  @Provides
  @Singleton
  fun provideApi(builder: Retrofit.Builder): GithubUserApi {
    return builder.build().create(GithubUserApi::class.java)
  }

  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
    return Retrofit.Builder()
      .baseUrl(ApiConstants.BASE_ENDPOINT)
      .client(okHttpClient)
      .addCallAdapterFactory(NetworkResponseAdapterFactory())
      .addConverterFactory(GsonConverterFactory.create())
  }

  @Provides
  fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
      .addInterceptor(httpLoggingInterceptor)
      .build()
  }

  @Provides
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
      interceptor.level = HttpLoggingInterceptor.Level.HEADERS
      interceptor.level = HttpLoggingInterceptor.Level.BODY
    }
    return interceptor
  }
}