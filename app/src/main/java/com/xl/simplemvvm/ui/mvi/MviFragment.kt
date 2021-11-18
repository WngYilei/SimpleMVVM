package com.xl.simplemvvm.ui.mvi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.xl.simplemvvm.bean.ArticleBean
import com.xl.simplemvvm.bean.BannerImg
import com.xl.simplemvvm.databinding.MainFragmentBinding
import com.xl.simplemvvm.item.ArticleItem
import com.xl.simplemvvm.item.TitleItem
import com.xl.simplemvvm.ui.compose.ComposeActivity
import com.xl.xl_base.adapter.image.ImageLoader
import com.xl.xl_base.adapter.recycler.*
import com.xl.xl_base.base.BaseFragment
import com.xl.xl_base.tool.ktx.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MviFragment : BaseFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {

    companion object {
        fun newInstance() = MviFragment()
        private const val TAG = "MainFragment"
    }

    private var page = 0
    private val mainViewModel by viewModels<MviViewModel>()
    private lateinit var recyclerAdapter: StableAdapter
    override fun onFragmentCreate(savedInstanceState: Bundle?) {

        viewBinding.smartRefresh.autoRefresh()

        viewBinding.smartRefresh.onSmartRefreshCallback {
            onRefresh {
                page = 0
                mainViewModel.getArtic(0)
            }
            onLoadMore {
                page += 1
                mainViewModel.getArtic(page)
            }
        }

        recyclerAdapter = createStableAdapter {
            imageLoader = ImageLoader(this@MviFragment)
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
            viewBinding.smartRefresh.finishRefresh()
            viewBinding.smartRefresh.finishLoadMore()
            if (!it.refresh) {
                it.banners.notEmpty {
                    val items = mutableListOf<TitleItem>()
                    it.forEach { article ->
                        items.add(TitleItem(article))
                    }
                    it.size.let { it1 ->
                        recyclerAdapter.submitList(it1, items, page == 0)
                    }
                }

                it.articleBean?.let {
                    val items = mutableListOf<ArticleItem>()
                    it.datas.forEach { article ->
                        items.add(ArticleItem(article))
                    }
                    it.datas.size.let { it1 ->
                        recyclerAdapter.submitList(it1, items, page == 0)
                    }
                }
            }
        }

        viewBinding.btn.setOnClickListener {
//            mainViewModel.getArtic(0)
            startActivity(Intent(context, ComposeActivity::class.java))
        }
    }

}