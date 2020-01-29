package com.example.myapplication.entities

data class Exercise(
    var name : String,
    var description : String,
    var repetition : Long,
    var sets : Long,
    var weight: Long,
    var weightGoal: Long

) {
    var id : Int = 0
    var weightStart: Long = 0

    var progressHistory = mutableListOf<String>()

}