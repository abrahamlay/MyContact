package com.abrahamlay.data.request


import com.google.gson.annotations.SerializedName

data class ContactRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phoneNumber")
    val phoneNumber: Int
)