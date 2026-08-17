package com.example.monaly.ui.fragment.register


// Importações :
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.monaly.R
import com.example.monaly.ui.viewmodel.RegisterViewModel
import com.google.android.material.textfield.TextInputEditText


// Fragment responsável por capturar o Nome, Sobrenome e Username na segunda etapa :
class RegisterProfileFragment : Fragment(R.layout.fragment_register_profile) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Conecta ao Cofre Compartilhado da Activity :
        val viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]


        val inputEditFirstName = view.findViewById<TextInputEditText>(R.id.inputEditRegisterFirstName)
        val inputEditLastName = view.findViewById<TextInputEditText>(R.id.inputEditRegisterLastName)
        val inputEditUsername = view.findViewById<TextInputEditText>(R.id.inputEditRegisterUsername)


        // Criamos um TextWatcher genérico para monitorar qualquer alteração nos campos :
        val textWatcher = object : TextWatcher {


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {


                val firstName = inputEditFirstName.text.toString()
                val lastName = inputEditLastName.text.toString()
                val username = inputEditUsername.text.toString()


                // Envia os dados para o Cofre validar :
                viewModel.validateProfile(firstName, lastName, username)


            }


        }


        // Aplica o ouvinte nos três campos de texto da tela :
        inputEditFirstName.addTextChangedListener(textWatcher)
        inputEditLastName.addTextChangedListener(textWatcher)
        inputEditUsername.addTextChangedListener(textWatcher)


    }


}