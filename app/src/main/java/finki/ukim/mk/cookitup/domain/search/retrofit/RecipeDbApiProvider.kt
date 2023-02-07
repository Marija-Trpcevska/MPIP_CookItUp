package finki.ukim.mk.cookitup.domain.search.retrofit

import com.google.gson.GsonBuilder
import finki.ukim.mk.cookitup.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RecipeDbApiProvider {

    companion object {
        @Volatile
        private var INSTANCE: RecipeDbApi? = null

        @JvmStatic
        fun getRecipeDbApi(): RecipeDbApi {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = createRecipeDbApi()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private fun createRecipeDbApi(): RecipeDbApi {
            class QueryParamInterceptor : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request: Request = chain.request()
                    val htt = request.url.newBuilder()
                        .addQueryParameter("app_key", BuildConfig.EDAMAM_APP_KEY)
                        .addQueryParameter("app_id", BuildConfig.EDAMAM_APP_ID)
                        .build()
                    request = request.newBuilder().url(htt).build()
                    return chain.proceed(request)
                }
            }

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okhttpClient = OkHttpClient.Builder()
                .addInterceptor(QueryParamInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val gsonConverterFactory = GsonConverterFactory.create(gson)

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.edamam.com")
                .client(okhttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()
            return retrofit.create(RecipeDbApi::class.java)
        }
    }
}