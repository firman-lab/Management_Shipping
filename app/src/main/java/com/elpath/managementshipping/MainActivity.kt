package com.elpath.managementshipping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.elpath.managementshipping.Login.LoginActivity
import com.elpath.managementshipping.SharedPrefManager.SharedPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Optional.of

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: FragmentViewModel

    companion object{
        const val PARAM_NAVIGATION_ID = "navigation_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(FragmentViewModel::class.java)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener())
        val navigationId = intent.getIntExtra(PARAM_NAVIGATION_ID, R.id.order)
        bottomNavigation.selectedItemId = navigationId
    }

    private fun onNavigationItemSelectedListener() = BottomNavigationView.OnNavigationItemSelectedListener{ item->
        loadFragment(item.itemId)
        true
    }
    private fun loadFragment(itemId: Int) {
        val tag = itemId.toString()
        var fragment = supportFragmentManager.findFragmentByTag(tag)?:when(itemId){
            R.id.order ->{
                OrderFragment()
            }
            R.id.buy->{
                BuyFragment()
            }
            else -> {
                null
            }
        }
        if (fragment!=null){

            val transaction = supportFragmentManager.beginTransaction()
            if (viewModel.lastActiveFragmentTag !=null) {
                val lastFragment =
                    supportFragmentManager.findFragmentByTag(viewModel.lastActiveFragmentTag)
                if (lastFragment != null)
                    transaction.hide(lastFragment)
            }
            if (!fragment.isAdded){
                transaction.add(R.id.mainFrame, fragment, tag)
            }
            else{
                transaction.show(fragment)
            }
            transaction.commit()
            viewModel.lastActiveFragmentTag = tag
        }
    }

    override fun onStart() {
        super.onStart()
        if (!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}