package br.edu.utfpr.consultacepcmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cep(
    val cep: String = "",
    val logradouro: String = "",
    val bairro: String = "",
    @SerialName("localidade")
    val localidade: String = "",
    val uf: String = ""
)