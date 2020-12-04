package com.abrahamlay.data.dtos


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("status")
    var status: String = "",
    @SerializedName("messages")
    var messages: List<String> = mutableListOf(),
    @SerializedName("data")
    var data: Data = Data()
)