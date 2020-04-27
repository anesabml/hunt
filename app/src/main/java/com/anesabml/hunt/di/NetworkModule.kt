package com.anesabml.hunt.di

import android.content.Context
import com.anesabml.hunt.BuildConfig
import com.anesabml.hunt.api.ApiService
import com.anesabml.hunt.utils.Constant
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.CacheKeyResolver
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object NetworkModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideApolloClient(context: Context, @Named("token") token: String): ApolloClient {
        // Create NormalizedCacheFactory
        // Please note that if null is passed in as the name, you will get an in-memory SQLite database that
        // will not persist across restarts of the app.
        val cacheFactory = SqlNormalizedCacheFactory(context, "hunt_cache")
        val resolver: CacheKeyResolver = object : CacheKeyResolver() {
            override fun fromFieldRecordSet(
                field: ResponseField,
                recordSet: Map<String, Any>
            ): CacheKey {
                return formatCacheKey(recordSet["id"] as String?)
            }

            override fun fromFieldArguments(
                field: ResponseField,
                variables: Operation.Variables
            ): CacheKey {
                return formatCacheKey(field.resolveArgument("id", variables) as String?)
            }

            private fun formatCacheKey(id: String?) = when {
                id.isNullOrEmpty() -> CacheKey.NO_KEY
                else -> CacheKey.from(id)
            }
        }
        return ApolloClient.builder()
            .serverUrl(Constant.GRAPHQL_API)
            .normalizedCache(cacheFactory, resolver)
            .okHttpClient(makeHttpClient(token = token))
            .build()
    }

    private fun makeHttpClient(debug: Boolean = BuildConfig.DEBUG, token: String): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        if (debug) {
            val httpLoggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        clientBuilder.addNetworkInterceptor {
            val original = it.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("access_token", token)
                .build()
            val requestBuilder = original.newBuilder().url(url)
            val request = requestBuilder.build()
            it.proceed(request)
        }
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideApiService(): ApiService {
        val okHttpClient = httpClient()
        return Retrofit.Builder()
            .baseUrl(Constant.PRODUCT_HUNT_API)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .build().create(ApiService::class.java)
    }

    private fun httpClient(debug: Boolean = BuildConfig.DEBUG): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()
        if (debug) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return clientBuilder.build()
    }
}
