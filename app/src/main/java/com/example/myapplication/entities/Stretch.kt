package com.example.myapplication.entities

data class Stretch(
    var name: String,
    var description: String,
    var seconds: Long,
    var sets: Long,
    var secondsGoal: Long

) {

    var id : Int = 0
    var secondsStart: Long = 0

    var progressHistory = mutableListOf<String>()

}
