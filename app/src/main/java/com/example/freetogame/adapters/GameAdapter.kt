package com.example.freetogame.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freetogame.data.Game
import com.example.freetogame.databinding.ItemGameBinding
import com.squareup.picasso.Picasso

class GameAdapter(var items: List<Game>) : RecyclerView.Adapter<GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGameBinding.inflate(layoutInflater, parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = items[position]
        holder.render(game)
    }

    override fun getItemCount(): Int = items.size

    fun updateData(dataSet: List<Game>) {
        items = dataSet
        notifyDataSetChanged()
    }
}

class GameViewHolder(val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(game: Game) {
        binding.titleTextView.text = game.title
        Picasso.get().load(game.image).into(binding.thumbnailImageView);
    }

}