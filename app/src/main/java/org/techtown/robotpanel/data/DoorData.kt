package org.techtown.robotpanel.data


import com.google.gson.annotations.SerializedName

data class DoorData(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: String,
    @SerializedName("header")
    val header: Header,
    @SerializedName("message")
    val message: String
) {
    data class Header(
        @SerializedName("requestCode")
        val requestCode: String
    )
}