package cz.utb.fai.cinenow

import android.app.Application
import cz.utb.fai.cinenow.api.TMDBApiService
import cz.utb.fai.cinenow.database.AppDatabase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CineNowApplication : Application() {

    private val database by lazy { AppDatabase.getDatabase(this) }

    private val apiService: TMDBApiService by lazy {
        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
                .build()
            chain.proceed(request)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(TMDBApiService::class.java)
    }

    val repository: Repository by lazy {
        Repository(apiService, database.movieDao())
    }
}
