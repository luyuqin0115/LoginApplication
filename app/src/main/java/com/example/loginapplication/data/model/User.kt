package com.example.loginapplication.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("collectIds")
    val collectIds: List<Int>
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class RegisterRequest(
    val username: String,
    val password: String,
    val repassword: String
)

data class ApiResponse<T>(
    @SerializedName("data")
    val data: T?,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String
) 