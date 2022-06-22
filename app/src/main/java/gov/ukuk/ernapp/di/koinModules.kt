package gov.ukuk.ernapp.di

import gov.ukuk.ernapp.data.network.networkModule
import gov.ukuk.ernapp.data.network.remoteDataSourceModule
import gov.ukuk.ernmessaging.di.viewModules

val koinModules = listOf(
    networkModule,
    remoteDataSourceModule,
    repoModules,
    viewModules
)