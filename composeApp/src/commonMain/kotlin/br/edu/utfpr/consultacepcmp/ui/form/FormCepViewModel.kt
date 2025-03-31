package br.edu.utfpr.consultacepcmp.ui.form

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.utfpr.consultacepcmp.data.model.Cep
import br.edu.utfpr.consultacepcmp.data.repository.CepRepository
import consultacepcmp.composeapp.generated.resources.Res
import consultacepcmp.composeapp.generated.resources.cep_invalido
import consultacepcmp.composeapp.generated.resources.cep_obrigatorio
import consultacepcmp.composeapp.generated.resources.erro_ao_carregar_cep
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

class FormCepViewModel(
    private val cepRepository: CepRepository,
) : ViewModel() {
    private val tag: String = "FormCepViewModel"

    var uiState: FormCepUiState by mutableStateOf(FormCepUiState())
        private set

    fun onCepAlterado(valor: String) {
        val novoCep = valor.replace("\\D".toRegex(), "")
        if (novoCep.length <= 8 && uiState.formState.cep.value != novoCep) {
            val mensagemValidacao = validarCep(novoCep)
            uiState = uiState.copy(
                formState = uiState.formState.copy(
                    cep = FormField(
                        value = novoCep,
                        errorStringResource = mensagemValidacao
                    )
                )
            )
        }
    }

    private fun validarCep(cep: String): StringResource? = if (cep.isBlank()) {
        Res.string.cep_obrigatorio
    } else if (cep.length != 8) {
        Res.string.cep_invalido
    } else {
        null
    }

    fun buscarCep(cep: String) {

        if (uiState.formState.buscandoCep) return

        uiState = uiState.copy(
            formState = uiState.formState.copy(
                buscandoCep = true,
                cep = FormField(
                    value = cep,
                    errorStringResource = null
                ),
                logradouro = FormField(""),
                bairro = FormField(""),
                localidade = FormField(""),
                uf = FormField("")
            )
        )
        viewModelScope.launch {
            delay(1500)
            uiState = try {
                val cepRetornado: Cep = cepRepository.buscarCep(cep)
                if (cepRetornado.logradouro.isBlank()) {
                    uiState.copy(
                        formState = uiState.formState.copy(
                            cep = FormField(
                                value = "",
                                errorStringResource = null
                            ),
                            logradouro = FormField(cepRetornado.logradouro),
                            bairro = FormField(cepRetornado.bairro),
                            localidade = FormField(cepRetornado.localidade),
                            uf = FormField(cepRetornado.uf),
                            buscandoCep = false
                        ),
                        ocorreuErroAoCarregar = true
                    )
                } else {
                    uiState.copy(
                        formState = uiState.formState.copy(
                            logradouro = FormField(cepRetornado.logradouro),
                            bairro = FormField(cepRetornado.bairro),
                            localidade = FormField(cepRetornado.localidade),
                            uf = FormField(cepRetornado.uf),
                            buscandoCep = false
                        )
                    )
                }
            } catch (ex: Exception) {
                println("[$tag]: Erro ao consultar o CEP $cep")
                ex.printStackTrace()
                uiState.copy(
                    formState = uiState.formState.copy(
                        buscandoCep = false,
                        cep = uiState.formState.cep.copy(
                            errorStringResource = Res.string.erro_ao_carregar_cep
                        )
                    )
                )
            }
        }
    }
}