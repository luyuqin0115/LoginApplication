package com.example.loginapplication.data.api

import com.example.loginapplication.data.model.ApiResponse
import com.example.loginapplication.data.model.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 使用Kotlin协程的API服务接口
 * 相比RxJava版本，使用suspend函数实现异步操作
 */
interface CoroutineApiService {
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录响应结果
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<User>
    
    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param repassword 确认密码
     * @return 注册响应结果
     */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): ApiResponse<User>
} 