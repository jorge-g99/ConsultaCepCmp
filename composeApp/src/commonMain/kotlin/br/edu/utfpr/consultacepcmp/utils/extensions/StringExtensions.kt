package br.edu.utfpr.consultacepcmp.utils.extensions

fun String.formatarCep(): String = mapIndexed { index, char ->
    when (index) {
        5 -> "-$char"
        else -> char
    }
}.joinToString("")