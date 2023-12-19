package org.techtown.robotpanel.data


import com.google.gson.annotations.SerializedName

data class PathListData(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("output")
    val output: Output
) {
    data class Output(
        @SerializedName("code")
        val code: String,
        @SerializedName("data")
        val `data`: Data,
        @SerializedName("message")
        val message: String
    ) {
        data class Data(
            @SerializedName("mapName")
            val mapName: String,
            @SerializedName("pathList")
            val pathList: String
        )
    }
}