package com.abrahamlay.domain.repoimpl

import com.abrahamlay.data.apis.ContactApi
import com.abrahamlay.data.dtos.Response
import com.abrahamlay.data.repository.ContactRepository
import com.abrahamlay.data.request.ContactDetailRequest
import com.abrahamlay.data.request.ContactRequest
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 2020-06-09.
 */


class ContactRepositoryImpl constructor(
    private val api: ContactApi
) : ContactRepository {

    override fun getContacts(): Flowable<Response> =
        api.getContacts()

    override fun getContactDetail(email: String): Flowable<Response> =
        api.getContactDetail(ContactDetailRequest(email))

    override fun addContact(contactRequest: ContactRequest): Flowable<Response> =
        api.addContact(contactRequest)

}