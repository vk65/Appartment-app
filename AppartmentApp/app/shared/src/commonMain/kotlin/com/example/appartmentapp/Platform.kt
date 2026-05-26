package com.example.appartmentapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform