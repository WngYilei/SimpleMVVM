package com.xl.simplemvvm.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xl.simplemvvm.R
import com.xl.simplemvvm.ui.mvi.MviFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MviFragment.newInstance())
                .commitNow()
        }
    }
}