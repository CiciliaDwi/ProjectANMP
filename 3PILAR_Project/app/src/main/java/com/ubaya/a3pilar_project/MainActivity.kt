package com.ubaya.a3pilar_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
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

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Bottom Navigation setup
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_budget -> navController.navigate(R.id.budgetFragment)
                R.id.menu_transaksi -> navController.navigate(R.id.transactionFragment)
                R.id.menu_profile -> navController.navigate(R.id.profileFragment)
                R.id.menu_report -> navController.navigate(R.id.reportFragment)
            }
            true
        }
    }
}