package com.ubaya.a3pilar_project.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ubaya.a3pilar_project.MainActivity
import com.ubaya.a3pilar_project.databinding.FragmentRegisterBinding
import com.ubaya.a3pilar_project.model.User
import com.ubaya.a3pilar_project.viewmodel.AuthViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val first = binding.etFirst.text.toString()
            val last = binding.etLast.text.toString()
            val pass = binding.etPassword.text.toString()
            val confirm = binding.etRepeatPassword.text.toString()

            val user = User(username, first, last, pass)
            viewModel.register(user, confirm)
        }

        viewModel.loginStatus.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Berhasil daftar & login", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}
