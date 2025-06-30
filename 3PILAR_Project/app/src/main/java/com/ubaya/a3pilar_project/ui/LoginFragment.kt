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
import com.ubaya.a3pilar_project.databinding.FragmentLoginBinding
import com.ubaya.a3pilar_project.viewmodel.AuthViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        // Aksi Login
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(username, password)
            } else {
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            }
        }

        // Aksi pindah ke register
        binding.tvToRegister.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace((view?.parent as ViewGroup).id, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }

        // Observe login sukses
        viewModel.loginStatus.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Login berhasil", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }

        // Observe error
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}
