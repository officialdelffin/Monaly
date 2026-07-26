package com.example.monaly.core.network


// Importações :
import android.content.Context
import android.net.ConnectivityManager
import kotlinx.coroutines.flow.Flow
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


// Classe que implementa e faz o monitoramento em tempo real da placa de rede do dispositivo :
class ConnectivityNetworkMonitor (private val context : Context) : NetworkMonitor {


    // Pegando a ferramenta de rede do android usando o passaporte context :
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    // Sobrescrevendo a propriedade do contrato com um construtor de fluxo reativo :
    override val isOnline: Flow<Boolean> = callbackFlow {


        // 1. Criamos o "ouvinte" oficial do Android para a placa de rede :
        val callback = object : ConnectivityManager.NetworkCallback() {


            // Acionado automaticamente quando o celular se conecta a uma rede válida :
            override fun onAvailable(network: Network) {


                // Injetamos o valor "true" dentro do fluxo :
                trySend(true)


            }


            // Acionado automaticamente quando o celular perde a conexão :
            override fun onLost(network: Network) {


                // Injetamos o valor "false" dentro do fluxo :
                trySend(false)


            }


        }


        // Acordamos o gerenciador do Android e mandamos ele usar o nosso ouvinte e checagem inicial imediata para sabermos se há internet no momento exato em que abrimos o app :
        connectivityManager.registerDefaultNetworkCallback(callback)
        val isCurrentlyConnected = connectivityManager.activeNetwork != null
        trySend(isCurrentlyConnected)


        // Bloqueio de segurança e limpeza (evita vazamento de memória) :
        awaitClose {


            // Quando o aplicativo fechar ou a tela for destruída, desativamos o ouvinte :
            connectivityManager.unregisterNetworkCallback(callback)


        }


    }


}