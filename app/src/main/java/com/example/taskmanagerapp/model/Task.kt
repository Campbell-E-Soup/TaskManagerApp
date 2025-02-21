package com.example.taskmanagerapp.model

class Task(desc: String) {
    //vars
    private val description: String = desc
    private var completed: Boolean = false
    //functions
    fun Check() {
        completed = !completed
    }

    //gets
    fun GetCompleted(): Boolean {
        return completed
    }

    fun GetDescription(): String {
        return description
    }
}