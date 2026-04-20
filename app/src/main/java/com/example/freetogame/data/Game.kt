package com.example.freetogame.data

import com.google.gson.annotations.SerializedName

data class Game (
    @SerializedName("id")           val id: Int,
    @SerializedName("title")        val title: String,
    @SerializedName("thumbnail")    val image: String
) {

}