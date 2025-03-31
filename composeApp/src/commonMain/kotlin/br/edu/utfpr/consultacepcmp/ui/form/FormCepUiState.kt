package br.edu.utfpr.consultacepcmp.ui.form

import org.jetbrains.compose.resources.StringResource

data class FormField(
    val value: String = "",
    val errorStringResource: StringResource? = null
)

data class FormState(
    val cep: FormField = FormField(),
    val logradouro: FormField = FormField(),
    val complemento: FormField = FormField(),
    val bairro: FormField = FormField(),
    val localidade: FormField = FormField(),
    val uf: FormField = FormField(),
    val buscandoCep: Boolean = false
)

data class FormCepUiState(
    val carregando: Boolean = false,
    val ocorreuErroAoCarregar: Boolean = false,
    val formState: FormState = FormState(),
)