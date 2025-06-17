package com.example.loginapplication.repository

import android.content.Context
import com.example.loginapplication.data.model.ApiResponse
import com.example.loginapplication.data.model.User
import com.example.loginapplication.network.NetworkManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class AuthRepository private constructor(context: Context) {
    
    companion object {
        @Volatile
        private var INSTANCE: AuthRepository? = null
        
        fun getInstance(context: Context): AuthRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AuthRepository(context).also { INSTANCE = it }
            }
        }
    }
    
    private val networkManager = NetworkManager.getInstance(context)
    
    fun login(username: String, password: String): Observable<ApiResponse<User>> {
        return networkManager.apiService.login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    
    fun register(username: String, password: String, repassword: String): Observable<ApiResponse<User>> {
        return networkManager.apiService.register(username, password, repassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    
    fun logout() {
        networkManager.clearCookies()
    }
    
    fun isLoggedIn(): Boolean {
        return networkManager.isLoggedIn()
    }
} 