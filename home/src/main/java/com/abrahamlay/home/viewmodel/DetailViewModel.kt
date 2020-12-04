package com.abrahamlay.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abrahamlay.core.subscriber.BaseViewModel
import com.abrahamlay.core.subscriber.DefaultSubscriber
import com.abrahamlay.core.subscriber.ResultState
import com.abrahamlay.data.dtos.Response
import com.abrahamlay.domain.interactors.GetContactDetail

class DetailViewModel(
    getContactDetail: GetContactDetail
) : BaseViewModel() {


    private var email: String = ""
    private val _getContactDetail = MutableLiveData<ResultState<Response>>()
    private val triggerFetch = MutableLiveData<Boolean>()
    val getContactDetail: LiveData<ResultState<Response>> =
        Transformations.switchMap(triggerFetch) {
            fetchContactDetail(getContactDetail)
            return@switchMap _getContactDetail
        }

    private fun fetchContactDetail(contactDetail: GetContactDetail) {
        contactDetail.execute(object : DefaultSubscriber<Response>() {
            override fun onError(error: ResultState<Response>) {
                _getContactDetail.postValue(error)
            }

            override fun onSuccess(data: ResultState<Response>) {
                _getContactDetail.postValue(data)
            }
        }, GetContactDetail.Params(email))
    }


    fun refreshContactDetail(email: String) {
        triggerFetch.value = true
        this.email = email
    }
}