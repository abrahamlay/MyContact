package com.abrahamlay.data.apis

import com.abrahamlay.data.dtos.Response
import com.abrahamlay.data.request.ContactDetailRequest
import com.abrahamlay.data.request.ContactRequest
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ContactApi {
    @GET("v1/contacts")
    fun getContacts(
    ): Flowable<Response>


    @POST("v1/contacts")
    fun addContact(
        @Body contactRequest: ContactRequest
    ): Flowable<Response>

    @POST("v1/contact/detail")
    fun getContactDetail(
        @Body contactDetailRequest: ContactDetailRequest
    ): Flowable<Response>

}