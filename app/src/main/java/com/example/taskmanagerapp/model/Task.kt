package com.example.taskmanagerapp.model

class Task(desc: String) {
    //vars
    private val description: String = desc
    private var completed: Boolean = false
    //functions
    fun check() {
        completed = !completed
    }

    //gets
    fun getCompleted(): Boolean {
        return completed
    }

    fun getDescription(): String {
        return description
    }
}