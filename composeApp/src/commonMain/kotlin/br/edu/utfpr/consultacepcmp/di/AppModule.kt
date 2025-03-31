package br.edu.utfpr.consultacepcmp.di

import br.edu.utfpr.consultacepcmp.data.repository.CepRepository
import br.edu.utfpr.consultacepcmp.ui.form.FormCepViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule: Module = module {
    singleOf(::CepRepository)
    viewModelOf(::FormCepViewModel)
}