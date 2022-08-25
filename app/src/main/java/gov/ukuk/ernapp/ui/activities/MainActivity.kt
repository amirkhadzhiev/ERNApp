package gov.ukuk.ernapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import gov.ukuk.ernapp.R
import gov.ukuk.ernapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        initNav()
    }

    private fun initNav() {
        navController = findNavController(R.id.main_container)
        binding.bottomNav.setupWithNavController(navController)
    }

    fun hideBottomNavigationView() {
        binding.bottomNav.clearAnimation()
        binding.bottomNav.animate()
            .translationY(binding.bottomNav.height.toFloat()).duration = 300
        binding.bottomNav.visibility = View.GONE
    }

    fun showBottomNavigationView() {
        binding.bottomNav.clearAnimation()
        binding.bottomNav.animate().translationY(0f).duration = 300
        binding.bottomNav.visibility = View.VISIBLE
    }
}