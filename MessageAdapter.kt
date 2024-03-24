package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.media.MediaPlayer
import android.util.Log
import java.io.IOException

class MessageAdapter(private val messagesList: List<Message>, private val onMessageLongClick: (messageId: String, currentText: String) -> Unit) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var mediaPlayer: MediaPlayer? = null


    private fun onPlay(audioUrl: String) {
        // Release any existing MediaPlayer instance
        mediaPlayer?.release()

        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(audioUrl)
                prepareAsync() // Asynchronously prepare the media player
                setOnPreparedListener { start() } // Start playback once prepared
            } catch (e: IOException) {
                Log.e("MediaPlayer", "Could not set data source", e)
            }
        }

        mediaPlayer?.setOnErrorListener { _, what, extra ->
            Log.e("MediaPlayer", "MediaPlayer error occurred: What: $what, Extra: $extra")
            true // Indicate that the error was handled
        }

        mediaPlayer?.setOnCompletionListener {
            it.release() // Release the MediaPlayer once playback is complete
            mediaPlayer = null
        }
    }



    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageText: TextView = view.findViewById(R.id.messageText)
        val playButton: ImageButton = view.findViewById(R.id.playButton) // Ensure this ID matches your layout


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messagesList[position]


        holder.itemView.setOnLongClickListener {
            onMessageLongClick(message.id, message.message ?: "") // Assuming your Message model has an 'id' field
            true // Consume the long click event
        }

        if (message.voiceUrl.isNullOrEmpty()) {
            // Text message
            holder.messageText.visibility = View.VISIBLE
            holder.playButton.visibility = View.GONE
            holder.messageText.text = message.message
        } else {
            // Voice message
            holder.messageText.visibility = View.GONE
            holder.playButton.visibility = View.VISIBLE
            holder.playButton.setOnClickListener {
                onPlay(message.voiceUrl!!)
            }
        }

    }

    override fun getItemCount(): Int = messagesList.size

    // Release MediaPlayer resources when the adapter is no longer used
    fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
