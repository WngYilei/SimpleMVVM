package com.xl.xl_base.adapter.recycler

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView


/**
NO_STABLE_IDS :
这个模式比较简单，就是指Adapter不支持stableId。

ISOLATED_STABLE_IDS  :
表示子Adapter之间采用隔离策略，在这个模式下，子Adapter不同考虑其他
Adapter的存在，因为在这个模式里面，ConcatAdapter 会覆盖子Adapter自
己生成的stableId，由它统一给每个item分配stableId，这样我们定义子Adapter
的时候，就不用其他的Adapter。注意的是，此时子Adapter的getItemId
方法和ViewHolder的getItemId方法的返回值是不一样的，我们如果需要stableId
的话，ViewHolder的getItemId方法是最可靠的。

SHARED_STABLE_IDS:
表示子Adapter之间采用共享策略，在这个模式，由子Adapter自己生成stableId，
ConcatAdapter不会覆盖子Adapter的stableId。因为stableId的唯一性原则，所
以每个子Adapter在生成stableId时需要考虑其他子Adapter的存在，必须保证生
成的stableId的唯一性。

 */

object AdapterConfig {
    @SafeVarargs
    fun createNo(vararg adapters: RecyclerView.Adapter<out RecyclerView.ViewHolder?>?): ConcatAdapter {
        val config = ConcatAdapter.Config.Builder()
            .setStableIdMode(ConcatAdapter.Config.StableIdMode.NO_STABLE_IDS).build()
        return ConcatAdapter(config, *adapters)
    }

    @SafeVarargs
    fun createIsolated(vararg adapters: RecyclerView.Adapter<out RecyclerView.ViewHolder?>?): ConcatAdapter {
        val config = ConcatAdapter.Config.Builder()
            .setStableIdMode(ConcatAdapter.Config.StableIdMode.ISOLATED_STABLE_IDS).build()
        return ConcatAdapter(config, *adapters)
    }


    @SafeVarargs
    fun createShare(vararg adapters: RecyclerView.Adapter<out RecyclerView.ViewHolder?>?): ConcatAdapter {
        val config = ConcatAdapter.Config.Builder()
            .setStableIdMode(ConcatAdapter.Config.StableIdMode.SHARED_STABLE_IDS).build()
        return ConcatAdapter(config, *adapters)
    }
}