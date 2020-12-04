package com.abrahamlay.data.dtos


import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("phoneNumber")
    val phoneNumber: Long = 0.toLong(),
    @SerializedName("createdAt")
    val createdAt: Long = 0.toLong()
)