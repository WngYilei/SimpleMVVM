package com.xl.simplemvvm.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.xl.common.tool.ktx.dp
import com.xl.simplemvvm.bean.ArticleBean
import com.xl.simplemvvm.bean.BannerImg
import com.xl.simplemvvm.databinding.MainFragmentBinding
import com.xl.simplemvvm.intent.MainState
import com.xl.simplemvvm.item.ArticleItem
import com.xl.simplemvvm.item.TitleItem
import com.xl.xl_base.adapter.image.ImageLoader
import com.xl.xl_base.adapter.recycler.GridDividerItemDecoration
import com.xl.xl_base.adapter.recycler.RecyclerAdapter
import com.xl.xl_base.adapter.recycler.createAdapter
import com.xl.xl_base.base.BaseFragment
import com.xl.xl_base.tool.ktx.collectFlow
import com.xl.xl_base.tool.ktx.onSmartRefreshCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {

    companion object {
        fun newInstance() = MainFragment()
        private const val TAG = "MainFragment"
    }

    var page = 0
    private val mainViewModel by viewModels<MainViewModel>()
    lateinit var recyclerAdapter: RecyclerAdapter
    override fun onFragmentCreate(savedInstanceState: Bundle?) {

        viewBinding.smartRefresh.autoRefresh()

        viewBinding.smartRefresh.onSmartRefreshCallback {
            onRefresh {
                page = 0
                mainViewModel.getArc2(0)
            }
            onLoadMore {
                page += 1
                mainViewModel.getArc2(page)
            }
        }
        recyclerAdapter = createAdapter {
            imageLoader = ImageLoader(this@MainFragment)
        }

        viewBinding.recycle.apply {
            adapter = recyclerAdapter
            layoutManager =
                LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            addItemDecoration(
                GridDividerItemDecoration(
                    0,
                    0.5.dp, Color.parseColor("#EEEEEE")
                )
            )
        }

        mainViewModel.state.collectFlow(this) {
            when (it) {
                is MainState.Loading -> {
                    Log.e(TAG, "Loading")
                }
                is MainState.Error -> {
                    Log.e("TAG", "onFragmentCreate: $it")
                }
                is MainState.Complete -> {
                    viewBinding.smartRefresh.finishRefresh()
                    viewBinding.smartRefresh.finishLoadMore()
                }
                is MainState.Body -> {
                    when (it.data) {
                        is ArticleBean -> {
                            val items = mutableListOf<ArticleItem>()
                            val data = it.data as ArticleBean
                            data.datas.forEach {
                                items.add(ArticleItem(it))
                            }
                            data.datas.size.let {
                                recyclerAdapter.submitList(it, items)
                            }
                        }
                        is BannerImg -> {

                        }
                    }
                }
                else -> {
                }
            }
        }

        viewBinding.btn.setOnClickListener {
            mainViewModel.getArc2(0)
        }
    }

}