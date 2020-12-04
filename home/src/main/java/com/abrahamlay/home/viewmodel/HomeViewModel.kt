package com.abrahamlay.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.abrahamlay.core.subscriber.BaseViewModel
import com.abrahamlay.core.subscriber.DefaultSubscriber
import com.abrahamlay.core.subscriber.ResultState
import com.abrahamlay.data.dtos.Response
import com.abrahamlay.data.request.ContactRequest
import com.abrahamlay.domain.interactors.AddContact
import com.abrahamlay.domain.interactors.GetContacts

class HomeViewModel(
    getContacts: GetContacts,
    private val addContact: AddContact
) : BaseViewModel() {
    private val _getContacts = MutableLiveData<ResultState<Response>>()
    private val triggerFetch = MutableLiveData<Boolean>()
    val getContacts: LiveData<ResultState<Response>> =
        Transformations.switchMap(triggerFetch) {
            fetchContacts(getContacts)
            return@switchMap _getContacts
        }

    private fun fetchContacts(repositoryImpl: GetContacts) {
        repositoryImpl.execute(object : DefaultSubscriber<Response>() {
            override fun onError(error: ResultState<Response>) {
                _getContacts.postValue(error)
            }

            override fun onSuccess(data: ResultState<Response>) {
                _getContacts.postValue(data)
            }
        }, null)
    }

    fun refreshContacts() {
        triggerFetch.value = true
    }

    private val _addContacts = MutableLiveData<ResultState<Response>>()
    val addContacts: LiveData<ResultState<Response>> = _addContacts

    fun addContacts(contactRequest: ContactRequest) {
        addContact.execute(object : DefaultSubscriber<Response>() {
            override fun onError(error: ResultState<Response>) {
                _addContacts.postValue(error)
            }

            override fun onSuccess(data: ResultState<Response>) {
                _addContacts.postValue(data)
            }
        }, AddContact.Params(contactRequest))
    }

}