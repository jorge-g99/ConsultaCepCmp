package br.edu.utfpr.consultacepcmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform