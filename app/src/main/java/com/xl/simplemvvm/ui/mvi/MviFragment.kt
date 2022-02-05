package com.xl.simplemvvm.ui.mvi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.xl.simplemvvm.databinding.MainFragmentBinding
import com.xl.simplemvvm.item.ArticleItem
import com.xl.simplemvvm.item.TextItem
import com.xl.simplemvvm.ui.compose.ComposeActivity
import com.xl.xl_base.adapter.image.ImageLoader
import com.xl.xl_base.adapter.item.ItemCell
import com.xl.xl_base.adapter.recycler.*
import com.xl.xl_base.base.BaseFragment
import com.xl.xl_base.tool.ktx.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*

@AndroidEntryPoint
class MviFragment : BaseFragment<MainFragmentBinding>(MainFragmentBinding::inflate) {

    companion object {
        fun newInstance() = MviFragment()
        private const val TAG = "MainFragment"
    }

    private var page = 0
    private val mainViewModel by viewModels<MviViewModel>()
    private lateinit var stableAdapter: StableAdapter
    private lateinit var recyclerAdapter: RecyclerAdapter
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

        stableAdapter = createStableAdapter {
            imageLoader = ImageLoader(this@MviFragment)
        }
        recyclerAdapter = createAdapter {
            imageLoader = ImageLoader(this@MviFragment)
        }



        viewBinding.recycle.apply {
            adapter = AdapterConfig.createNo(recyclerAdapter, stableAdapter)
            layoutManager =
                LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            addItemDecoration(
                GridDividerItemDecoration(
                    0,
                    0.5.dp, Color.parseColor("#EEEEEE")
                )
            )
        }


        recyclerAdapter.submitList(1, arrayListOf(TextItem("这是第一个item"), TextItem("这是第二个item")))
        mainViewModel.state.collectHandlerFlow(this) {
            it.articleBean?.let {
                viewBinding.smartRefresh.finishRefresh()
                viewBinding.smartRefresh.finishLoadMore()


                val items = mutableListOf<ItemCell>()
                it.datas.forEach { article ->
                    items.add(ArticleItem(article))
                }
                it.datas.size.let { it1 ->
                    stableAdapter.submitList(it1, items, page == 0)
                }
            }
        }


    }

}