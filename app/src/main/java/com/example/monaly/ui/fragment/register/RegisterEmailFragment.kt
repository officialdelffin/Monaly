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
import com.google.android.material.textfield.TextInputLayout


// Fragment responsável por capturar e validar o e-mail na primeira etapa :
class RegisterEmailFragment : Fragment(R.layout.fragment_register_email) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Conectamos ao cofre compartilhado usando o requireActivity para garantir que é o mesmo da RegisterActivity :
        val viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]


        val inputLayout = view.findViewById<TextInputLayout>(R.id.inputLayoutRegisterEmail)
        val inputEdit = view.findViewById<TextInputEditText>(R.id.inputEditRegisterEmail)


        // Adiciona um ouvinte para observar cada letra que o usuário digita em tempo real :
        inputEdit.addTextChangedListener(object : TextWatcher {


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {


                val emailText = s.toString()


                // Envia o texto para o cofre validar :
                viewModel.validateEmail(emailText)


                // Feedback visual UX que ostra erro em vermelho se o formato for inválido e não estiver vazio :
                if (viewModel.isEmailValid.value || emailText.isEmpty()) {


                    inputLayout.error = null


                } else {


                    inputLayout.error = "Formato de e-mail inválido"


                }


            }


        })


    }


}