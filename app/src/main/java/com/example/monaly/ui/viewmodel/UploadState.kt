package com.example.monaly.ui.viewmodel


// Definindo o contrato de estados de carregamento de forma centralizada e reutilizável :
sealed class UploadState {


    object Idle : UploadState()
    data class Uploading(val current: Int, val total: Int) : UploadState()
    object Success : UploadState()
    data class Error(val message: String) : UploadState()
    object Loading : UploadState()


}