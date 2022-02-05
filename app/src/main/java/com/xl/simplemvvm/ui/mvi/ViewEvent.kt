package com.xl.simplemvvm.ui.mvi

sealed class ViewEvent {
    object Refresh : ViewEvent()
    object RefreshFollow : ViewEvent()
    object RefreshType : ViewEvent()
    object RefreshToppoc : ViewEvent()
    object RefreshNewInfo : ViewEvent()
    object RefreshRecommend : ViewEvent()

    object RefreshWeekRanking :ViewEvent()
    object RefreshMonthRanking :ViewEvent()
    object RefreshTotalRanking :ViewEvent()
}
