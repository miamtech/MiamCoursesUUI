package tech.miam.coursesUDemoApp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ai.mealz.core.Mealz
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.miam.coursesUDemoApp.features.miam.template.MiamSdkHelper
import org.koin.android.ext.android.inject
import tech.miam.coursesUDemoApp.basket.BasketLocalDataSource
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private val basketLocalDataSource by inject<BasketLocalDataSource>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MiamSdkHelper.initialize(
            appContext =  this,
            storeId = "25910",
            userId = "test_${UUID.randomUUID()}",
            basket = basketLocalDataSource.getProducts()
        )

        CoroutineScope(Dispatchers.Main).launch {
            Mealz.notifications.availability.listen {
                if (it) {
                    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                    val navController = navHostFragment.navController
                    val fragmentContainerView = findViewById<FragmentContainerView>(R.id.nav_host_fragment)
                    val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                    findViewById<BottomNavigationView>(R.id.activity_main_bottom_nav_view).setupWithNavController(navController)
                    fragmentContainerView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }
            }
        }
    }
}