package com.samarth.ktornoteapp.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.samarth.ktornoteapp.R
import com.samarth.ktornoteapp.databinding.FragmentUserInfoBinding

class UserInfoFragment:Fragment(R.layout.fragment_user_info) {

    private var _binding: FragmentUserInfoBinding? = null
    val binding: FragmentUserInfoBinding?
        get() = _binding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserInfoBinding.bind(view)


        binding?.createAccountBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_userInfoFragment_to_createAccountFragment)
        }

        binding?.loginBtn?.setOnClickListener {
            findNavController().navigate(R.id.action_userInfoFragment_to_loginFragment)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}