package com.example.popularmovies

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initNavUi()
    }

    override fun onSupportNavigateUp(): Boolean {

        return findNavController(R.id.main_nav_host).navigateUp()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {

        return dispatchingAndroidInjector
    }

    private fun initNavUi() {

        val navController = Navigation.findNavController(this, R.id.main_nav_host)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.dest_main)
        )

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

}
