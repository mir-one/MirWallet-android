package com.wavesplatform.sdk.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

data class AssetsInfoResponse(
        @SerializedName("__type") var type: String = "list",
        @SerializedName("data") var data: List<AssetsInfoData> = listOf()
)

data class AssetsInfoData(
        @SerializedName("__type") var type: String = "asset",
        @SerializedName("data") var assetInfo: AssetInfo = AssetInfo()
)

@Parcelize
open class AssetInfo(
        @SerializedName("ticker") var ticker: String? = "",
        @SerializedName("id") var id: String = "",
        @SerializedName("name") var name: String = "",
        @SerializedName("precision") var precision: Int = 0,
        @SerializedName("description") var description: String = "",
        @SerializedName("height") var height: Int = 0,
        @SerializedName("timestamp") var timestamp: Date = Date(),
        @SerializedName("sender") var sender: String = "",
        @SerializedName("quantity") var quantity: Long = 0,
        @SerializedName("hasScript") var hasScript: Boolean = false,
        @SerializedName("minSponsoredFee") var minSponsoredFee: Long = 0,
        @SerializedName("reissuable") var reissuable: Boolean = false,
        var isSpam: Boolean = false
) : Parcelable {
    fun isSponsored(): Boolean {
        return minSponsoredFee > 0
    }
}