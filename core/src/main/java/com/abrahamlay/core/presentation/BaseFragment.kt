package com.abrahamlay.core.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.abrahamlay.core.subscriber.BaseViewModel

@SuppressLint("Registered")
abstract class BaseFragment<VM : BaseViewModel> : Fragment() {
    abstract val viewModel: VM
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitViews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onInitObservers()
    }

    protected open fun onInitViews() = Unit

    protected open fun onInitObservers() = Unit
}