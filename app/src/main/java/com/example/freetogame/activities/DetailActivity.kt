package com.example.freetogame.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.freetogame.R
import com.example.freetogame.data.Game
import com.example.freetogame.data.GameService
import com.example.freetogame.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.net.toUri


class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getIntExtra("GAME_ID", -1)

        CoroutineScope(Dispatchers.IO).launch {
            game = GameService.getInstance().getGameById(id)

            CoroutineScope(Dispatchers.Main).launch {
                loadData()
            }
        }
    }

    fun loadData() {
        supportActionBar?.title = game.title

        binding.titleTextView.text = game.title
        Picasso.get().load(game.image).into(binding.thumbnailImageView)
        binding.playButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(game.gameURL.toUri())
            startActivity(intent)
        }
    }
}