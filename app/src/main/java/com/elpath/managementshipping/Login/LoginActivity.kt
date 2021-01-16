package com.elpath.managementshipping.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.elpath.managementshipping.MainActivity
import com.elpath.managementshipping.Network.RetrofitClient
import com.elpath.managementshipping.R
import com.elpath.managementshipping.SharedPrefManager.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btnLogin.setOnClickListener {
            val username = findViewById<EditText>(R.id.edtUsername)
            val password = findViewById<EditText>(R.id.edtPass)
            val loading = findViewById<ProgressBar>(R.id.loading)
            loading.visibility = View.VISIBLE
            btnLogin.isEnabled = false
            btnLogin.isClickable =false



            val usr = username.text.toString().trim()
            val pass = password.text.toString().trim()

            when {
                usr.isEmpty() -> {
                    username.error = "username can't be empty!"
                    username.requestFocus()
                    loading.visibility = View.GONE
                }
                pass.isEmpty() -> {
                    password.error = "password can't be empty!"
                    password.requestFocus()
                    loading.visibility = View.GONE
                }
                else -> {
                    RetrofitClient.instances.login(usr, pass)
                        .enqueue(object : Callback<LoginResponse> {

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
                                loading.visibility = View.GONE
                                btnLogin.isEnabled = true
                                btnLogin.isClickable = true
                            }

                            override fun onResponse(
                                call: Call<LoginResponse>, response: Response<LoginResponse>
                            ) {
                                if (!response.body()?.error!!) {
                                    SharedPrefManager.getInstance(applicationContext)
                                        .saveLogin(response.body()?.login!!)
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    loading.visibility = View.GONE
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        response.body()?.message!!,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    loading.visibility = View.GONE
                                    btnLogin.isEnabled = true
                                    btnLogin.isClickable = true
                                }
                            }
                        })
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}