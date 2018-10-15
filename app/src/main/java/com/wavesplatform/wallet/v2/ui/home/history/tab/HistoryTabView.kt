package com.wavesplatform.wallet.v2.ui.home.history.tab

import com.wavesplatform.wallet.v2.ui.base.view.BaseMvpView
import com.wavesplatform.wallet.v2.data.model.local.HistoryItem

interface HistoryTabView :BaseMvpView{
    fun afterSuccessLoadTransaction(data: ArrayList<HistoryItem>, type: String?)
}