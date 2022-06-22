package gov.ukuk.ernapp.data.network

import gov.ukuk.ernapp.constants.const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single{ provideRetrofit(get()) }
    factory { provideOkHttpClient() }
    factory { provideApi(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
    return Retrofit.Builder()
        .baseUrl(const.BASE_URL_ERN)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(): OkHttpClient{
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    return OkHttpClient().newBuilder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(interceptor).build()
}

fun provideApi(retrofit: Retrofit): ERNAppApiService =
    retrofit.create(ERNAppApiService::class.java)