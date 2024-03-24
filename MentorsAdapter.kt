package com.example.myapplication
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class MentorsAdapter(private val mentorsList: List<Mentor>, private val onClick: (Mentor) -> Unit) : RecyclerView.Adapter<MentorsAdapter.MentorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.screen_14, parent, false)
        return MentorViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MentorViewHolder, position: Int) {
        val mentor = mentorsList[position]
        holder.bind(mentor)
    }

    override fun getItemCount() = mentorsList.size

    class MentorViewHolder(itemView: View, val onClick: (Mentor) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val mentorNameTextView: TextView = itemView.findViewById(R.id.msg_id)

        fun bind(mentor: Mentor) {
            mentorNameTextView.text = mentor.Name
            itemView.setOnClickListener { onClick(mentor) }
        }
    }
}
