package com.example.monaly.core.network


// Importações :
import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.flow.Flow


// Classe que implementa e faz o monitoramento em tempo real da placa de rede do dispositivo :
class ConnectivityNetworkMonitor (private val context : Context) : NetworkMonitor{


    // Pegando a ferramenta de rede do android usando o passaporte context :
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    // Sobrescrevendo a propriedadade que o NetworkMonitor exige a criação :
    override val isOnline: Flow<Boolean>
        get() = TODO("Not yet implemented")


}