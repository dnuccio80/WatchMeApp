package com.example.watchme.core

sealed class Sections(val title:String){
    data object Episodes: Sections("episodes")
    data object Suggested: Sections("suggested")
    data object Details: Sections("details")
    data object Media: Sections("media")
    data object Credits: Sections("credits")
}
