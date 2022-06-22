package gov.ukuk.ernapp.di

import gov.ukuk.ernapp.data.repository.Repository
import org.koin.core.module.Module
import org.koin.dsl.module

val repoModules: Module = module {
    single { Repository(get()) }
}