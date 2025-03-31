package br.edu.utfpr.consultacepcmp

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.edu.utfpr.consultacepcmp.ui.form.FormCepScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    modifier: Modifier = Modifier,
) {
    MaterialTheme {
        FormCepScreen(
            modifier = modifier,
        )
    }
}

