package com.example.watchme.core

sealed class Interpretations(val interpretation:String){
    data object All: Interpretations("All")
    data object Cast: Interpretations("Cast")
    data object Crew: Interpretations("Crew")
}