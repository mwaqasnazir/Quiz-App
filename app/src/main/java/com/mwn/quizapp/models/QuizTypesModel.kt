package com.mwn.quizapp.models

// Data Class to Show the data in Quiz Types of RV
data class QuizTypesModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val time: String,
    val questionList: List<QuestionModel>
){
    constructor(): this("","","","", emptyList())
}

// Data class to store questions for quiz
data class QuestionModel(
    val question: String,
    val options: List<String>,
    val answer: String
){
    constructor():this("", emptyList(),"")
}