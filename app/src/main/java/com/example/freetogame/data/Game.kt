package com.example.freetogame.data

import com.google.gson.annotations.SerializedName

data class Game (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val image: String,
    @SerializedName("short_description") val shortDescription: String,
    @SerializedName("description") val description: String?,
    @SerializedName("game_url") val gameURL: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("platform") val platform: String,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("developer") val developer: String,
    @SerializedName("minimum_system_requirements") val systemRequirements: SystemRequirements?,
    @SerializedName("screenshots") val screenshots: List<Screenshot>?,
) {

}

data class SystemRequirements(
    @SerializedName("os") val os: String,
    @SerializedName("processor") val processor: String,
    @SerializedName("memory") val memory: String,
    @SerializedName("graphics") val graphics: String,
    @SerializedName("storage") val storage: String,
)

data class Screenshot(
    @SerializedName("image") val image: String,
)