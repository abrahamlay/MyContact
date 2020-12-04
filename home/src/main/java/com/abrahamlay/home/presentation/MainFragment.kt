package com.abrahamlay.home.presentation

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abrahamlay.core.presentation.BaseListFragment
import com.abrahamlay.core.subscriber.BaseViewModel
import com.abrahamlay.core.subscriber.ResultState
import com.abrahamlay.data.dtos.Contact
import com.abrahamlay.data.dtos.Response
import com.abrahamlay.data.request.ContactRequest
import com.abrahamlay.home.R
import com.abrahamlay.home.presentation.adapter.ContactsAdapter
import com.abrahamlay.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.view_error.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : BaseListFragment<Response, BaseViewModel>(),
    ContactsAdapter.OnClickListener {

    override val viewModel by viewModel<HomeViewModel>()

    private var selectedOrderBy: Int = 0
    private var selectedOrderFrom: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onInitObservers() {
        super.onInitObservers()
        setHasOptionsMenu(true)
        adapter = ContactsAdapter()
        (adapter as? ContactsAdapter)?.onClickListener = this
        viewModel.getContacts.observe(this, {
            onContactsLoaded(it)
        })

        viewModel.addContacts.observe(this, {
            onAddContactResult(it)
        })
        onRefresh()
    }

    private fun onAddContactResult(it: ResultState<Response>?) {
        hideLoading()
        when (it) {
            is ResultState.Success -> {
                onRefresh()
            }
            is ResultState.Error -> {
                Toast.makeText(requireContext(), it.throwable.message, Toast.LENGTH_SHORT).show()
            }
            else -> {
                //
            }
        }
    }

    private fun onContactsLoaded(it: ResultState<Response>?) {
        hideLoading()
        when (it) {
            is ResultState.Success -> {
                initAdapter(it.data.data.contacts)
            }
            is ResultState.Error -> {
                showError(it.throwable)
            }
            is ResultState.Loading -> {
                initAdapter(it.data.data.contacts)
            }
        }
    }

    private fun initAdapter(list: List<Contact>?) {
        list?.let {
            (adapter as? ContactsAdapter)?.data = list.sortedBy { it.name }
            rvList.adapter = adapter
            rvList.layoutManager = getLayoutManager()
        }
    }

    private fun changeOrderBy(selectedOrderBy: Int, selectedOrderFrom: Int) {
        val temp = (adapter as? ContactsAdapter)?.data ?: listOf()

        (adapter as? ContactsAdapter)?.data =
            when (selectedOrderFrom) {
                0 -> temp.sortedBy { setOrderBy(selectedOrderBy, it) }
                else -> temp.sortedByDescending { setOrderBy(selectedOrderBy, it) }
            }
        (adapter as? ContactsAdapter)?.notifyDataSetChanged()
    }

    private fun setOrderBy(selected: Int, it: Contact) =
        when (selected) {
            0 -> it.name
            1 -> it.email
            2 -> it.phoneNumber.toString()
            3 -> it.createdAt.toString()
            else -> it.name
        }

    override fun onRefresh() {
        showLoading()
        hideError()
        viewModel.refreshContacts()
    }

    override fun onItemClicked(data: Any) {
        Toast.makeText(context, (data as Contact).name, Toast.LENGTH_SHORT).show()
        NavHostFragment.findNavController(this)
            .navigate(
                R.id.action_mainFragment_to_detailFragment,
                bundleOf(Pair(DetailFragment.PARAM_EMAIL, data.email))
            )
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refresh?.setOnRefreshListener(this)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        // Do something that differs the Activity's menu here
        MenuInflater(requireContext()).inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sortBy -> {
                showOrderDialog()
                return true
            }
            R.id.addContact -> {
                showAddContactDialog()
                return true
            }
        }
        return false
    }


    private fun showOrderDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Order by")
        val orderBy = arrayOf(
            "Name",
            "Email",
            "Phone",
            "Created Date"
        )

        builder.setSingleChoiceItems(
            orderBy, selectedOrderBy
        ) { dialog, item ->
            Toast.makeText(
                requireContext(),
                "Order By = " + orderBy[item], Toast.LENGTH_SHORT
            ).show()
            selectedOrderBy = item
            showOrderFromDialog()
            dialog.dismiss()
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    private fun showAddContactDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Add Contact")
        // Get the layout inflater
        val inflater = requireActivity().layoutInflater
        var alert: AlertDialog? = null
        builder.setView(inflater.inflate(R.layout.dialog_add_contact, null, false))
        builder.setPositiveButton(
            "Add Contact"
        ) { dialog, id ->
            dialog.dismiss()
            showLoading()
            viewModel.addContacts(
                ContactRequest(
                    alert?.findViewById<EditText>(R.id.etDialogName)?.text.toString(),
                    alert?.findViewById<EditText>(R.id.etDialogEmail)?.text.toString(),
                    alert?.findViewById<EditText>(R.id.etDialogPhoneNumber)?.text.toString().toInt()
                )
            )
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, id ->
            dialog.dismiss()
        }

        alert = builder.create()
        // Add action buttons

        alert.show()
    }

    private fun showOrderFromDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Order from")
        val orderFrom = arrayOf("Ascending", "Descending")
        builder.setSingleChoiceItems(orderFrom, selectedOrderFrom) { dialog, item ->
            selectedOrderFrom = item
            changeOrderBy(selectedOrderBy, selectedOrderFrom)
            dialog.dismiss()
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }
}