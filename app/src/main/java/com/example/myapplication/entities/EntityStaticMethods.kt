package com.example.myapplication.entities

fun calculateProgress(start: Long, current: Long, goal: Long) : Int {

    val total = goal - start
    val progress = goal - current
    val percent = (progress.toDouble() / total) * 100

    return 100 - percent.toInt()
}

