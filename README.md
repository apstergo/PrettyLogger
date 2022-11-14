# PrettyLogger
PrettyLogger – удобный логгер для okhttp и retrofit.
<br/>
PrettyLogger is a handy logger for okhttp and retrofit.
<br/>
#Как подключить/How to use#
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}


}

dependencies {
        implementation 'com.github.apstergo:PrettyLogger:1.0.0'
}
```
#Пример использования/Sample#

```
val okHttpClient = okHttpClientBuilder
            .addInterceptor(HttpLoggingInterceptor(prettyLogger).setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
 ```
 
