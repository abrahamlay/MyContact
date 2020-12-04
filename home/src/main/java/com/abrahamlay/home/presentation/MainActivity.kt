package com.abrahamlay.home.presentation

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import com.abrahamlay.core.presentation.BaseActivity
import com.abrahamlay.core.subscriber.BaseViewModel
import com.abrahamlay.home.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<BaseViewModel>() {
    private lateinit var navHostFragment: NavHostFragment
    override val viewModel by viewModel<BaseViewModel>()
    override val resourceLayout: Int?
        get() = R.layout.activity_main


    override fun onInitViews() {
        super.onInitViews()
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
    }

    override fun onBackPressed() {
        if (!navHostFragment.navController.popBackStack())
            finish()
        else
            navHostFragment.navController.navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (!navHostFragment.navController.popBackStack())
                    finish()
                else
                    navHostFragment.navController.navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
