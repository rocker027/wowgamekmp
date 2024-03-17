package main

import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel {
    val titleState = MutableStateFlow("")
    val environmentState = MutableStateFlow("")


    fun updateTitle(title: String) {
        titleState.value = title
    }

    fun updateEnvironment(environment: String) {
        environmentState.value = environment
    }
}