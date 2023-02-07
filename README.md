# PrettyLogger
PrettyLogger – удобный логгер для okhttp и retrofit.
<br/>
<br/>
PrettyLogger is a handy logger for okhttp and retrofit.
<br/>
# Как подключить/How to use #
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}


}

dependencies {
        implementation 'com.github.apstergo:PrettyLogger:1.0.3'
}
```
# Пример использования/Sample #

## okhttp ##

```
val prettyLogger by lazy { PrettyLogger {
                Log.d("TAG", it)
            }
        }

val okHttpClient = okHttpClientBuilder
            .addInterceptor(HttpLoggingInterceptor(prettyLogger).setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
```

 ## Ktor ##

```
val client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
                val prettyLogger =  PrettyLoggerKtor { Log.d("TAG KTOR", it) }
                logger = prettyLogger
            }
```
