package com.mwn.quizapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mwn.quizapp.QuizActivity
import com.mwn.quizapp.databinding.QuizItemRvRowBinding
import com.mwn.quizapp.models.QuizTypesModel

class QuizAdapter(private val list: List<QuizTypesModel>) :
    RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {
    inner class QuizViewHolder(private val binding: QuizItemRvRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: QuizTypesModel) {
            binding.apply {

                quizTitleTxt.text = model.title

                quizSubtitleTxt.text = model.subtitle

                timerTxt.text = model.time + "Min"

                root.setOnClickListener {
                    Intent(root.context, QuizActivity::class.java).apply {

                        QuizActivity.questionModelList = model.questionList

                        QuizActivity.time = model.time

                        root.context.startActivity(this)
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val binding =
            QuizItemRvRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuizViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.bind(list[position])
    }
}