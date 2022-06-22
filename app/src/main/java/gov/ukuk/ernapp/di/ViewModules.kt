package gov.ukuk.ernmessaging.di

import gov.ukuk.ernapp.ui.fragments.searchPlate.SearchViewModel
import gov.ukuk.ernapp.ui.fragments.searchPIN.SearchPinViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModules: Module = module {
    viewModel { SearchViewModel(get()) }
    viewModel { SearchPinViewModel(get()) }

}