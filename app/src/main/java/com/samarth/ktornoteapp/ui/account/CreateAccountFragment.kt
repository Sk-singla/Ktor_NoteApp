package com.samarth.ktornoteapp.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.samarth.ktornoteapp.R
import com.samarth.ktornoteapp.databinding.FragmentCreateAccountBinding

class CreateAccountFragment: Fragment(R.layout.fragment_create_account) {


    private var _binding: FragmentCreateAccountBinding? = null
    val binding:FragmentCreateAccountBinding?
        get() = _binding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreateAccountBinding.bind(view)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}