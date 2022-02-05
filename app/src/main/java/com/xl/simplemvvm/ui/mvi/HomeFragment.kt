package com.xl.simplemvvm.ui.mvi

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xl.simplemvvm.databinding.HomeFragmentBinding
import com.xl.simplemvvm.item.BannerItem
import com.xl.simplemvvm.item.HomeVideoItem
import com.xl.simplemvvm.item.TextHeaderItem
import com.xl.simplemvvm.ui.main.MainFragment
import com.xl.xl_base.adapter.image.ImageLoader
import com.xl.xl_base.adapter.item.ItemCell
import com.xl.xl_base.adapter.recycler.*
import com.xl.xl_base.base.BaseFragment
import com.xl.xl_base.tool.ktx.collectHandlerFlow
import com.xl.xl_base.tool.ktx.dp
import com.xl.xl_base.tool.ktx.goActivity
import com.xl.xl_base.tool.ktx.onSmartRefreshCallback
import com.xl.xl_base.tool.util.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>(HomeFragmentBinding::inflate) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel by viewModels<MviViewModel>()
    private lateinit var bannerAdapter: StableAdapter
    private lateinit var recyclerAdapter: StableAdapter
    private var date: String = ""
    override fun onFragmentCreate(savedInstanceState: Bundle?) {



        viewBinding.smartRefresh.autoRefresh()

        viewBinding.smartRefresh.onSmartRefreshCallback {
            onRefresh {
                viewModel.submitAction(ViewEvent.Refresh)
            }
            onLoadMore {
                viewModel.getNextHome(date)
            }
        }

        recyclerAdapter = createStableAdapter {
            imageLoader = ImageLoader(this@HomeFragment)

        }

        bannerAdapter = createStableAdapter {
            imageLoader = ImageLoader(this@HomeFragment)
        }

        viewBinding.recycle.apply {
            adapter = AdapterConfig.createNo(bannerAdapter, recyclerAdapter)
            layoutManager =
                LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.VERTICAL
                }
            addItemDecoration(
                GridDividerItemDecoration(
                    0,
                    0.5.dp, Color.parseColor("#EEEEEE")
                )
            )
        }


        viewModel.state.collectHandlerFlow(this) { state ->

            state.homeInfo?.let { it ->
                viewBinding.smartRefresh.finishRefresh()
                viewBinding.smartRefresh.finishLoadMore()

                val map = StringUtils.getUrl(it.nextPageUrl)
                date = map["date"]!!
                val items = mutableListOf<ItemCell>()



                it.issueList[0].itemList.forEach {
                    if (it.type == "video") {
                        items.add(HomeVideoItem(it.data))
                    }
                    else if (it.type == "textHeader") {
                        items.add(TextHeaderItem(it.data.text))
                    }
                }
                it.issueList[0].itemList.size?.let {
                    recyclerAdapter.submitList(it, items, state.refresh)
                }
            }
        }

    }

}