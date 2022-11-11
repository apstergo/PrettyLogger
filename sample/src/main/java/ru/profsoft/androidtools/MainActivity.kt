package ru.profsoft.androidtools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.moczul.ok2curl.CurlInterceptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.profsoft.prettylogger.PrettyLogger
import timber.log.Timber
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.buttonTest)
        button.setOnClickListener {
            prettyLoggerExample()
        }
    }

    fun prettyLoggerExample() {
        val prettyLogger by lazy { PrettyLogger() }
        val okHttpClientBuilder = OkHttpClient.Builder()
        val okHttpClient = okHttpClientBuilder
            .addInterceptor(HttpLoggingInterceptor(prettyLogger).setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val retrofitSevice = retrofit.create(RetrofitSevice::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val posts = retrofitSevice.getPosts()
        }

    }
}