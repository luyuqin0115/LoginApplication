package com.example.loginapplication.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.loginapplication.repository.AuthRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class AuthViewModel(private val context: Context) : ViewModel() {
    
    private val authRepository = AuthRepository.getInstance(context)
    private val compositeDisposable = CompositeDisposable()
    
    // UI状态
    val isLoading = mutableStateOf(false)
    val loginResult = mutableStateOf<String?>(null)
    val registerResult = mutableStateOf<String?>(null)
    val isLoggedIn = mutableStateOf(authRepository.isLoggedIn())
    
    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            loginResult.value = "用户名和密码不能为空"
            return
        }
        
        isLoading.value = true
        loginResult.value = null
        
        val disposable = authRepository.login(username, password)
            .subscribe(
                { response ->
                    isLoading.value = false
                    if (response.errorCode == 0) {
                        loginResult.value = "登录成功"
                        isLoggedIn.value = true
                    } else {
                        loginResult.value = response.errorMsg
                    }
                },
                { error ->
                    isLoading.value = false
                    loginResult.value = "网络错误: ${error.message}"
                }
            )
        
        compositeDisposable.add(disposable)
    }
    
    fun register(username: String, password: String, repassword: String) {
        if (username.isBlank() || password.isBlank() || repassword.isBlank()) {
            registerResult.value = "所有字段都不能为空"
            return
        }
        
        if (password != repassword) {
            registerResult.value = "两次输入的密码不一致"
            return
        }
        
        if (password.length < 6) {
            registerResult.value = "密码长度不能少于6位"
            return
        }
        
        isLoading.value = true
        registerResult.value = null
        
        val disposable = authRepository.register(username, password, repassword)
            .subscribe(
                { response ->
                    isLoading.value = false
                    if (response.errorCode == 0) {
                        registerResult.value = "注册成功"
                        isLoggedIn.value = true
                    } else {
                        registerResult.value = response.errorMsg
                    }
                },
                { error ->
                    isLoading.value = false
                    registerResult.value = "网络错误: ${error.message}"
                }
            )
        
        compositeDisposable.add(disposable)
    }
    
    fun logout() {
        authRepository.logout()
        isLoggedIn.value = false
        loginResult.value = null
        registerResult.value = null
    }
    
    fun clearMessages() {
        loginResult.value = null
        registerResult.value = null
    }
    
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
} 