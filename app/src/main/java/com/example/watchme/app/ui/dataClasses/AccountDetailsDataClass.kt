package com.example.watchme.app.ui.dataClasses

data class AccountDetailsDataClass(
    val id: Int,
    val isoLocation: String,
    val isoCountry: String,
    val name: String,
    val includeAdult: Boolean,
    val username: String,
)
