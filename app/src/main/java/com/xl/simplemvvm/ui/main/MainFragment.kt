package com.xl.simplemvvm.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xl.simplemvvm.bean.ArticleBean
import com.xl.simplemvvm.bean.BannerImg
import com.xl.simplemvvm.databinding.MainFragmentBinding
import com.xl.simplemvvm.item.ArticleItem
import com.xl.simplemvvm.item.TitleItem
import com.xl.xl_base.adapter.image.ImageLoader
import com.xl.xl_base.adapter.recycler.*
import com.xl.xl_base.base.BaseFragment
import com.xl.xl_base.tool.ktx.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*

@AndroidEntryPoint
class MainFragment : BaseFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {

    companion object {
        fun newInstance() = MainFragment()
        private const val TAG = "MainFragment"
    }

    private var page = 0
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var recyclerAdapter: StableAdapter
    override fun onFragmentCreate(savedInstanceState: Bundle?) {

        viewBinding.smartRefresh.autoRefresh()

        viewBinding.smartRefresh.onSmartRefreshCallback {
            onRefresh {
                page = 0
                mainViewModel.getArc(0)
            }
            onLoadMore {
                page += 1
                mainViewModel.getArc(page)
            }
        }
        recyclerAdapter = createStableAdapter {
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


        mainViewModel.state.collectHandlerFlow(this) {
            loading {
                Log.e(TAG, "loading: ")
            }
            onResult {
                when (it) {
                    is ArticleBean -> {
                        val items = mutableListOf<ArticleItem>()
                        it.datas.forEach { article ->
                            items.add(ArticleItem(article))
                        }
                        it.datas.size.let { it1 ->
                            recyclerAdapter.submitList(it1, items, page == 0)
                        }
                    }
                    is ArrayList<*> -> {
                        val items = mutableListOf<TitleItem>()
                        it.forEach { any ->
                            items.add(TitleItem(any as BannerImg))
                        }
                        it.size.let { it ->
                            recyclerAdapter.submitList(it, items, page == 0)
                        }
                    }
                }
            }
            complete {
                viewBinding.smartRefresh.finishLoadMore()
                viewBinding.smartRefresh.finishRefresh()
            }
            onError {
                Log.e(TAG, "onError: ")
            }
        }


        viewBinding.btn.setOnClickListener {
            mainViewModel.getBanner()
        }
    }

}