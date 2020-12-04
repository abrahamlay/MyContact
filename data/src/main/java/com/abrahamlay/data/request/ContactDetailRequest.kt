package com.abrahamlay.data.request


import com.google.gson.annotations.SerializedName

data class ContactDetailRequest(
    @SerializedName("email")
    val email: String
)