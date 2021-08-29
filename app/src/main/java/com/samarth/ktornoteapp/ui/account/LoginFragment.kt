package com.samarth.ktornoteapp.ui.account

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.samarth.ktornoteapp.R
import com.samarth.ktornoteapp.databinding.FragmentLoginBinding
import com.samarth.ktornoteapp.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment:Fragment(R.layout.fragment_login) {


    private var _binding: FragmentLoginBinding? = null
    val binding: FragmentLoginBinding?
        get() = _binding

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        subscribeToLoginEvents()

        binding?.loginBtn?.setOnClickListener {
            val name = binding!!.nameEditTxt.text.toString()
            val email = binding!!.emailEditTxt.text.toString()
            val password = binding!!.passwordEdtTxt.text.toString()

            userViewModel.loginUser(
                name.trim(),
                email.trim(),
                password.trim()
            )
        }

    }

    private fun subscribeToLoginEvents() = lifecycleScope.launch{
        userViewModel.loginState.collect { result ->
            when(result){
                is Result.Success -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Account Successfully Created!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is Result.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showProgressBar()
                }
            }

        }
    }

    private fun showProgressBar(){
        binding?.loginProgressBar?.isVisible = true
    }

    private fun hideProgressBar(){
        binding?.loginProgressBar?.isVisible = false
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}