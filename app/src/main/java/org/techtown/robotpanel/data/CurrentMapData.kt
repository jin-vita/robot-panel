package org.techtown.robotpanel.data


import com.google.gson.annotations.SerializedName

data class CurrentMapData(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("output")
    val output: Output
) {
    data class Output(
        @SerializedName("currmap")
        val currmap: String,
        @SerializedName("imgname")
        val imgname: String
    )
}