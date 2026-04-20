package com.example.freetogame.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GameService {

    @GET("api/games")
    suspend fun getGamesList() : List<Game>

    @GET("api/game")
    suspend fun getGameById(@Query("id") id: Int) : Game

    companion object {
        fun getInstance() : GameService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.freetogame.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(GameService::class.java)
        }
    }
}