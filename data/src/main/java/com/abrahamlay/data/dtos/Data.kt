package com.abrahamlay.data.dtos


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("contacts")
    var contacts: List<Contact> = mutableListOf(),
    @SerializedName("contact")
    var contact: List<Contact> = mutableListOf()
)