package com.ubaya.a3pilar_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ubaya.a3pilar_project.databinding.ActivityMainBinding
import com.ubaya.a3pilar_project.ui.BudgetFragment
import com.ubaya.a3pilar_project.ui.ProfileFragment
import com.ubaya.a3pilar_project.ui.ReportFragment
import com.ubaya.a3pilar_project.ui.TransactionFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Tampilkan TransactionFragment sebagai default
        loadFragment(TransactionFragment())

        // 🔽 Navigasi bottom menu
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_budget -> loadFragment(BudgetFragment())
                R.id.menu_transaksi -> loadFragment(TransactionFragment())
                R.id.menu_profile -> loadFragment(ProfileFragment())
                R.id.menu_report -> loadFragment(ReportFragment())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}
