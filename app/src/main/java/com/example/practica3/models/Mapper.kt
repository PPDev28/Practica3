package com.example.practica3.models

interface Mapper<I, O> {
    fun map(input: I): O
}