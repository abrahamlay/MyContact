package com.abrahamlay.domain.interactors

import com.abrahamlay.data.dtos.Response
import com.abrahamlay.data.repository.ContactRepository
import com.abrahamlay.domain.FlowableUseCase
import com.abrahamlay.domain.PostExecutionThread
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 10/06/20.
 */
class GetContacts constructor(
    private val repository: ContactRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<Response, Void?>(postExecutionThread) {
    override fun build(params: Void?): Flowable<Response> {
        return repository.getContacts()
    }
}