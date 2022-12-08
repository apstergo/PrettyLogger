package ru.profsoft.androidtools

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.profsoft.prettylogger.PrettyLogger
import ru.profsoft.prettylogger.PrettyLoggerKtor
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://jsonplaceholder.typicode.com"
private const val GET_POSTS = "$BASE_URL/posts"
class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var buttonKtor: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.buttonOkHttp)
        button.setOnClickListener {
            prettyLoggerExample()
        }
        buttonKtor = findViewById(R.id.buttonKtor)
        buttonKtor.setOnClickListener {
            prettyLoggerKtorExample()
        }
    }

    fun prettyLoggerExample() {
        val prettyLogger by lazy {
            PrettyLogger {
                Log.d("TAG OkHttp", it)
            }
        }
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
    fun prettyLoggerKtorExample()
    {
        val client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
                val prettyLogger =  PrettyLoggerKtor { Log.d("TAG KTOR", it) }
                logger = prettyLogger
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            val data = client.get(GET_POSTS)
        }
    }
}