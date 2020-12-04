package com.abrahamlay.core.presentation

import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.abrahamlay.core.subscriber.BaseViewModel


abstract class BaseListFragment<DATA, VM : BaseViewModel> : BaseFragment<VM>(),
    SwipeRefreshLayout.OnRefreshListener {

    protected var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    protected var mItemList: List<DATA>? = null

}