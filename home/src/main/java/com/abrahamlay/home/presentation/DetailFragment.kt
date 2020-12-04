package com.abrahamlay.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.abrahamlay.core.presentation.BaseFragment
import com.abrahamlay.core.subscriber.ResultState
import com.abrahamlay.data.dtos.Contact
import com.abrahamlay.home.R
import com.abrahamlay.home.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.main_fragment.errorView
import kotlinx.android.synthetic.main.main_fragment.refresh
import kotlinx.android.synthetic.main.view_error.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DetailFragment : BaseFragment<DetailViewModel>(), SwipeRefreshLayout.OnRefreshListener {

    private val argsEmail: String by lazy { arguments?.getString(PARAM_EMAIL) ?: "" }

    override val viewModel by viewModel<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onInitObservers() {
        super.onInitObservers()
        viewModel.getContactDetail.observe(this) {
            hideLoading()
            when (it) {
                is ResultState.Success -> {
                    if (it.data.data.contact.isNotEmpty()) {
                        setView(it.data.data.contact[0])
                    } else {
                        showError(Throwable("Data Not Found..."))
                    }
                }
                is ResultState.Error -> {
                    showError(it.throwable)
                }
                else -> {
                    setView(Contact())
                }
            }
        }
        onRefresh()
    }

    override fun onInitViews() {
        super.onInitViews()
        refresh?.setOnRefreshListener(this)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        activity?.actionBar?.setDisplayShowHomeEnabled(true)
        activity?.actionBar?.setHomeButtonEnabled(true)
        setHasOptionsMenu(true)
    }

    private fun setView(contact: Contact) {
        tvDetailName.text = contact.name
        tvDetailEmail.text = contact.email
        tvDetailPhoneNumber.text = contact.phoneNumber.toString()
        tvDetailCreatedDate.text = Date(contact.createdAt).toString()
    }


    companion object {
        const val PARAM_EMAIL = "PARAM_EMAIL"
    }

    override fun onRefresh() {
        hideError()
        showLoading()
        viewModel.refreshContactDetail(argsEmail)
    }


    private fun showLoading() {
        refresh?.isRefreshing = true
    }

    private fun hideLoading() {
        refresh?.isRefreshing = false
    }

    private fun showError(throwable: Throwable) {
        errorView?.visibility = View.VISIBLE
        errorView?.tvErrorMessage?.text = throwable.localizedMessage
        errorView?.btnRetry?.setOnClickListener { onRefresh() }
    }

    private fun hideError() {
        errorView?.visibility = View.GONE
    }
}