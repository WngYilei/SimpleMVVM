package com.xl.simplemvvm.ui.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import com.xl.simplemvvm.bean.BannerImg
import com.xl.simplemvvm.ui.main.MainViewModel
import com.xl.simplemvvm.ui.theme.SimpleMVVMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {
    private val model: ComposeViewModel by viewModels()
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleMVVMTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Button(onClick = { model.getBanner() }) {
                        Text(text = "获取数据")
                    }
                    val data = model.state.collectAsState()
                    Log.e("TAG", "onCreate: $data")
                    if (data.value.banners.isNotEmpty()) {
                        LazyColumn {
                            itemsIndexed(data.value.banners) { _, item ->
                                Text(text = item.title)
                            }
                        }
                    }
                }

            }

        }

    }
}
