package com.abrahamlay.domain.interactors

import com.abrahamlay.data.dtos.Response
import com.abrahamlay.data.repository.ContactRepository
import com.abrahamlay.data.request.ContactRequest
import com.abrahamlay.domain.FlowableUseCase
import com.abrahamlay.domain.PostExecutionThread
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 10/06/20.
 */
class AddContact constructor(
    private val repository: ContactRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<Response, AddContact.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<Response> {
        return repository.addContact(params.contactRequest)
    }

    data class Params(val contactRequest: ContactRequest)
}