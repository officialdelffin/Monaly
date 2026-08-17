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


// Fragment responsável por capturar e validar a senha na terceira etapa :
class RegisterPasswordFragment : Fragment(R.layout.fragment_register_password) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Conecta ao Cofre Compartilhado da Activity :
        val viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]


        val inputLayout = view.findViewById<TextInputLayout>(R.id.inputLayoutRegisterPassword)
        val inputEdit = view.findViewById<TextInputEditText>(R.id.inputEditRegisterPassword)


        // Monitora cada letra digitada no campo de senha :
        inputEdit.addTextChangedListener(object : TextWatcher {


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {


                val passwordText = s.toString()


                // Envia para o Cofre validar as regras de letras e números :
                viewModel.validatePassword(passwordText)


                // Feedback visual UX: Mostra erro em vermelho se a senha for fraca e não estiver vazia :
                if (viewModel.isPasswordValid.value || passwordText.isEmpty()) {


                    inputLayout.error = null



                }


                else {


                    inputLayout.error = "A senha deve ter ao menos 6 caracteres (apenas letras e números)"


                }


            }


        })


    }


}