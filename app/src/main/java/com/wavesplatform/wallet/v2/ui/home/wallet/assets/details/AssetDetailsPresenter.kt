package com.wavesplatform.wallet.v2.ui.home.wallet.assets.details

import com.arellomobile.mvp.InjectViewState
import com.vicpin.krealmextensions.queryAllAsSingle
import com.wavesplatform.wallet.v2.data.model.remote.response.AssetBalance
import com.wavesplatform.wallet.v2.data.model.remote.response.Transaction
import com.wavesplatform.wallet.v2.ui.base.presenter.BasePresenter
import com.wavesplatform.wallet.v2.ui.home.wallet.assets.AssetsAdapter
import com.wavesplatform.wallet.v2.util.RxUtil
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import pyxis.uzuki.live.richutilskt.utils.runAsync
import pyxis.uzuki.live.richutilskt.utils.runOnUiThread
import javax.inject.Inject

@InjectViewState
class AssetDetailsPresenter @Inject constructor() : BasePresenter<AssetDetailsView>() {
    var needToUpdate: Boolean = false
    var isShow = true
    var scrollRange: Float = -1f
    var allTransaction: List<Transaction> = emptyList()

    fun loadAssets(itemType: Int) {
        runAsync {
            addSubscription(Single.zip(queryAllAsSingle<AssetBalance>(), queryAllAsSingle<Transaction>(),
                    BiFunction { assets: List<AssetBalance>, transactions: List<Transaction> ->
                        allTransaction = transactions
                        return@BiFunction assets
                    })
                    .map {
                        return@map when (itemType) {
                            AssetsAdapter.TYPE_SPAM_ASSET -> {
                                it.asSequence().filter { it.isSpam }.toMutableList()
                            }
                            AssetsAdapter.TYPE_HIDDEN_ASSET -> {
                                it.asSequence().filter { it.isHidden && !it.isSpam }.sortedBy { it.position }.toMutableList()
                            }
                            AssetsAdapter.TYPE_ASSET -> {
                                it.asSequence().filter { !it.isHidden && !it.isSpam }.sortedByDescending { it.isGateway }.sortedBy { it.position }.sortedByDescending { it.isFavorite }.toMutableList()
                            }
                            else -> {
                                it.asSequence().filter { !it.isHidden && !it.isSpam }.sortedByDescending { it.isGateway }.sortedBy { it.position }.sortedByDescending { it.isFavorite }.toMutableList()
                            }
                        }
                    }
                    .compose(RxUtil.applySingleDefaultSchedulers())
                    .subscribe({ it ->
                        runOnUiThread {
                            viewState.afterSuccessLoadAssets(it)
                        }
                    }, {
                        it.printStackTrace()
                    }))
        }
    }
}
