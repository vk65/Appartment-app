package com.example.testmultiplatformapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform