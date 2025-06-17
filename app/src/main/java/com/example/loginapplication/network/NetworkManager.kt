package com.example.loginapplication.network

import android.content.Context
import android.content.SharedPreferences
import com.example.loginapplication.data.api.ApiService
import com.google.gson.GsonBuilder
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkManager private constructor(context: Context) {
    
    companion object {
        @Volatile
        private var INSTANCE: NetworkManager? = null
        
        fun getInstance(context: Context): NetworkManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: NetworkManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("login_cookies", Context.MODE_PRIVATE)
    
    private val cookieJar = PersistentCookieJar(sharedPreferences)
    
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .cookieJar(cookieJar)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
    
    private val gson = GsonBuilder()
        .setLenient()
        .create()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.wanandroid.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    
    val apiService: ApiService = retrofit.create(ApiService::class.java)
    
    fun clearCookies() {
        cookieJar.clearCookies()
    }
    
    fun isLoggedIn(): Boolean {
        return cookieJar.hasCookies()
    }
}

class PersistentCookieJar(private val sharedPreferences: SharedPreferences) : CookieJar {
    
    private val cookieStore = mutableMapOf<String, MutableList<Cookie>>()
    
    init {
        loadCookies()
    }
    
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val host = url.host
        val hostCookies = cookieStore[host] ?: mutableListOf()
        
        for (cookie in cookies) {
            // 移除已存在的同名cookie
            hostCookies.removeAll { it.name == cookie.name }
            // 只保存未过期的cookie
            if (!cookie.persistent || cookie.expiresAt > System.currentTimeMillis()) {
                hostCookies.add(cookie)
            }
        }
        
        cookieStore[host] = hostCookies
        saveCookies()
    }
    
    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val host = url.host
        val hostCookies = cookieStore[host] ?: return emptyList()
        
        // 过滤掉已过期的cookie
        val validCookies = hostCookies.filter { cookie ->
            !cookie.persistent || cookie.expiresAt > System.currentTimeMillis()
        }
        
        // 更新存储，移除过期的cookie
        if (validCookies.size != hostCookies.size) {
            cookieStore[host] = validCookies.toMutableList()
            saveCookies()
        }
        
        return validCookies
    }
    
    private fun saveCookies() {
        val editor = sharedPreferences.edit()
        editor.clear()
        
        for ((host, cookies) in cookieStore) {
            val cookieStrings = cookies.map { "${it.name}=${it.value}" }
            editor.putStringSet(host, cookieStrings.toSet())
        }
        
        editor.apply()
    }
    
    private fun loadCookies() {
        cookieStore.clear()
        
        for ((host, cookieStrings) in sharedPreferences.all) {
            if (cookieStrings is Set<*>) {
                val cookies = mutableListOf<Cookie>()
                for (cookieString in cookieStrings) {
                    if (cookieString is String) {
                        val parts = cookieString.split("=", limit = 2)
                        if (parts.size == 2) {
                            val cookie = Cookie.Builder()
                                .name(parts[0])
                                .value(parts[1])
                                .domain(host)
                                .build()
                            cookies.add(cookie)
                        }
                    }
                }
                cookieStore[host] = cookies
            }
        }
    }
    
    fun clearCookies() {
        cookieStore.clear()
        sharedPreferences.edit().clear().apply()
    }
    
    fun hasCookies(): Boolean {
        return cookieStore.isNotEmpty() && cookieStore.values.any { it.isNotEmpty() }
    }
} 