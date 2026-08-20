package com.example.monaly.ui.fragment


// Importações :
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.monaly.R
import com.example.monaly.ui.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch


// Fragmento responsável por exibir os dados finais antes do envio :
class RegisterSummaryFragment : Fragment() {


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        // Infla o layout XML da etapa 4 que construímos :
        return inflater.inflate(R.layout.fragment_register_summary, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Conecta com o mesmo Cofre (ViewModel) compartilhado pela Activity :
        val viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]


        // Mapeia os campos de texto na tela :
        val textEmail = view.findViewById<TextView>(R.id.textSummaryEmail)
        val textName = view.findViewById<TextView>(R.id.textSummaryName)
        val textUsername = view.findViewById<TextView>(R.id.textSummaryUsername)


        // Observa e preenche o E-mail em tempo real :
        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.email.collect { email ->


                textEmail.text = email


            }


        }


        // Observa e preenche o Nome Completo (juntando Nome e Sobrenome) :
        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.firstName.collect { firstName ->


                textName.text = "$firstName ${viewModel.lastName.value}"


            }


        }


        // Observa e preenche o Username :
        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.username.collect { username ->


                textUsername.text = username


            }


        }


    }


}