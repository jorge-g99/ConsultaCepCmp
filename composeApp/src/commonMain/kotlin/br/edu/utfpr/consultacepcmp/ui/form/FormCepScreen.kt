package br.edu.utfpr.consultacepcmp.ui.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.edu.utfpr.consultacepcmp.ui.form.visualtransformation.CepVisualTransformation
import consultacepcmp.composeapp.generated.resources.Res
import consultacepcmp.composeapp.generated.resources.bairro
import consultacepcmp.composeapp.generated.resources.buscar
import consultacepcmp.composeapp.generated.resources.digite_o_cep
import consultacepcmp.composeapp.generated.resources.erro_ao_carregar_cep
import consultacepcmp.composeapp.generated.resources.logradouro
import consultacepcmp.composeapp.generated.resources.uf
import consultacepcmp.composeapp.generated.resources.localidade
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormCepScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    viewModel: FormCepViewModel = koinViewModel<FormCepViewModel>()
) {
    LaunchedEffect(snackbarHostState, viewModel.uiState.ocorreuErroAoCarregar) {
        if (viewModel.uiState.ocorreuErroAoCarregar) {
            snackbarHostState.showSnackbar(
                message = getString(Res.string.erro_ao_carregar_cep)
            )
        }
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        Formulario(
            modifier = Modifier.padding(innerPadding),
            formState = viewModel.uiState.formState,
            onCepAlterado = viewModel::onCepAlterado,
            buscarCep = viewModel::buscarCep
        )
    }
}

@Composable
fun Formulario(
    modifier: Modifier = Modifier,
    formState: FormState,
    onCepAlterado: (String) -> Unit,
    buscarCep: (String) -> Unit
) {
   Column(
       modifier = modifier
           .fillMaxSize()
           .padding(vertical = 16.dp)
           .verticalScroll(rememberScrollState())
   ) {
       FormTextField(
           label = stringResource(Res.string.digite_o_cep),
           value = formState.cep.value,
           onValueChanged = onCepAlterado,
           errorStringResource = formState.cep.errorStringResource,
           keyboardType = KeyboardType.Number,
           visualTransformation = CepVisualTransformation(),
           enabled = !formState.buscandoCep,
           trailingIcon = {
               if (formState.buscandoCep) {
                   CircularProgressIndicator(
                       modifier = Modifier
                           .size(60.dp)
                           .padding(16.dp),
                       strokeWidth = 2.dp
                   )
               }
           }
       )
       FormButton(
           modifier = Modifier
               .align(Alignment.CenterHorizontally)
               .padding(16.dp),
           onClick = {
               buscarCep(formState.cep.value)
           },
           enabled = formState.cep.value.length == 8
       )
       FormTextField(
           label = stringResource(Res.string.logradouro),
           value = formState.logradouro.value,
           readOnly = true,
           onValueChanged = {}
       )
       FormTextField(
           label = stringResource(Res.string.bairro),
           value = formState.bairro.value,
           readOnly = true,
           onValueChanged = {}
       )
       FormTextField(
           label = stringResource(Res.string.localidade),
           value = formState.localidade.value,
           readOnly = true,
           onValueChanged = {},
       )
       FormTextField(
           label = stringResource(Res.string.uf),
           value = formState.uf.value,
           readOnly = true,
           onValueChanged = {},
       )
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    errorStringResource: StringResource? = null,
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    keyboardImeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onValueChange = onValueChanged,
        label = { Text(label) },
        maxLines = 1,
        enabled = enabled,
        readOnly = readOnly,
        isError = errorStringResource != null,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            imeAction = keyboardImeAction,
            keyboardType = keyboardType
        ),
        visualTransformation = visualTransformation
    )
    errorStringResource?.let {
        Text(
            text = stringResource(errorStringResource),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun FormButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = false
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Text(
            text = stringResource(Res.string.buscar),
            style = MaterialTheme.typography.titleMedium
        )
    }
}
