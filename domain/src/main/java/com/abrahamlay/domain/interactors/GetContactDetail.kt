package com.abrahamlay.domain.interactors

import com.abrahamlay.data.dtos.Response
import com.abrahamlay.data.repository.ContactRepository
import com.abrahamlay.domain.FlowableUseCase
import com.abrahamlay.domain.PostExecutionThread
import io.reactivex.Flowable

/**
 * Created by Abraham Lay on 10/06/20.
 */
class GetContactDetail constructor(
    private val repository: ContactRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<Response, GetContactDetail.Params>(postExecutionThread) {
    override fun build(params: Params): Flowable<Response> {
        return repository.getContactDetail(params.email)
    }

    class Params(val email: String)
}