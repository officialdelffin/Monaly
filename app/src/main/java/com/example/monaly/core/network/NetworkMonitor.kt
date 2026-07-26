package com.example.monaly.core.network


// Importações :
import kotlinx.coroutines.flow.Flow


// Contrato que vai definir sempre a entrega do status da rede de forma continua :
interface NetworkMonitor {


    // Aqui onde vai ser definido esse status de true e false de forma continua :
    val isOnline : Flow<Boolean>


}