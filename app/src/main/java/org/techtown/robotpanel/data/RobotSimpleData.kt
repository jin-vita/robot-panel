package org.techtown.robotpanel.data


import com.google.gson.annotations.SerializedName

data class RobotSimpleData(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String
)