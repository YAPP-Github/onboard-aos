package com.yapp.bol.presentation.utils

import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * LoadStateAdapter를 사용하기 위해 ConcatAdapter를 만들어주기 위해
 * + withLoadStateHeaderAndFooter
 * + withLoadStateHeader
 * + withLoadStateFooter
 *
 * 함수를 사용할 때 initial load의 경우에는 LoadState에 따른 UI가 적용되지 않아
 * LoadStateListener를 커스텀해준 함수입니다.
 */
fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        header.loadState = loadStates.refresh
        footer.loadState = loadStates.append
    }

    return ConcatAdapter(header, this, footer)
}
