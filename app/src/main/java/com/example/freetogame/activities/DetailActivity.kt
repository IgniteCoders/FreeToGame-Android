package com.example.freetogame.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.freetogame.R
import com.example.freetogame.adapters.ScreenshotAdapter
import com.example.freetogame.data.Game
import com.example.freetogame.data.GameService
import com.example.freetogame.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getIntExtra("GAME_ID", -1)

        CoroutineScope(Dispatchers.IO).launch {
            game = GameService.getInstance().getGameById(id)

            CoroutineScope(Dispatchers.Main).launch {
                loadData()
            }
        }

        binding.showMoreButton.setOnClickListener {
            if (binding.descriptionTextView.maxLines == 5) {
                binding.descriptionTextView.maxLines = Int.MAX_VALUE
                binding.showMoreButton.text = "Show less"
            } else {
                binding.descriptionTextView.maxLines = 5
                binding.showMoreButton.text = "Show more"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_share -> {
                // Share
                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Mira que juego esta disponible gratis: \n${game.profileURL}")
                sendIntent.setType("text/plain")

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun loadData() {
        // Header
        supportActionBar?.title = game.title
        binding.titleTextView.text = game.title
        Picasso.get().load(game.image).into(binding.thumbnailImageView)

        // Info
        binding.genreTextView.text = game.genre
        binding.platformTextView.text = game.platform
        binding.developerTextView.text = game.developer
        binding.publisherTextView.text = game.publisher

        // Description
        binding.descriptionTextView.text = game.description

        // Screenshots
        game.screenshots?.let {
            val adapter = ScreenshotAdapter(it)
            binding.screenshotsRecyclerView.adapter = adapter
        }

        binding.playButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(game.gameURL.toUri())
            startActivity(intent)
        }
    }
}