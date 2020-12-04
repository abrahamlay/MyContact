package com.abrahamlay.data.repository

import com.abrahamlay.data.dtos.Response
import com.abrahamlay.data.request.ContactRequest
import io.reactivex.Flowable

interface ContactRepository {
    fun getContacts(): Flowable<Response>
    fun getContactDetail(email: String): Flowable<Response>
    fun addContact(contactRequest: ContactRequest): Flowable<Response>
}