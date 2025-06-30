package com.ubaya.a3pilar_project.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.ubaya.a3pilar_project.data.AppDatabase
import com.ubaya.a3pilar_project.databinding.FragmentProfileBinding
import com.ubaya.a3pilar_project.ui.AuthActivity
import kotlinx.coroutines.launch
import com.ubaya.a3pilar_project.data.UserDao


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.fragment = this
        return binding.root
    }

    fun onChangePasswordClicked() {
        val oldPass = binding.etOldPassword.text.toString()
        val newPass = binding.etNewPassword.text.toString()
        val repeatPass = binding.etRepeatPassword.text.toString()

        val shared = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        val username = shared.getString("username", null)

        if (username != null) {
            lifecycleScope.launch {
                val db = AppDatabase.getInstance(requireContext())
                var user = db.userDao().getUserByUsername(username)

                if (user != null && user.password == oldPass) {
                    if (newPass == repeatPass) {
                        val updatedUser = user.copy(password = newPass)
                        db.userDao().update(updatedUser)
                        Toast.makeText(requireContext(), "Password berhasil diubah", Toast.LENGTH_SHORT).show()
                        binding.etOldPassword.setText("")
                        binding.etNewPassword.setText("")
                        binding.etRepeatPassword.setText("")
                    } else {
                        Toast.makeText(requireContext(), "Password baru tidak cocok", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(requireContext(), "Password lama salah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun onLogoutClicked() {
        val shared = requireContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        shared.edit().clear().apply()

        val intent = Intent(requireContext(), AuthActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}
