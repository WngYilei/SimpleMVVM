package com.xl.simplemvvm.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xl.simplemvvm.R
import com.xl.simplemvvm.databinding.MainActivityBinding
import com.xl.simplemvvm.ui.mvi.HomeFragment
import com.xl.simplemvvm.ui.mvi.MviFragment
import com.xl.xl_base.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityBinding>(MainActivityBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}