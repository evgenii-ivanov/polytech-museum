package ru.sfedu.ictis.museumapp.models

data class Exhibit (
    val name: String,
    val description: String,
    val categories: String,
    val image: List<String>
)