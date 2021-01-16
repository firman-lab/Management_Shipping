package com.elpath.managementshipping.SharedPrefManager

import android.content.Context
import com.elpath.managementshipping.Login.Login

class SharedPrefManager private constructor(private val mCtx: Context){

    val isLoggedIn : Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("username", null) !=null
        }

    val login : Login
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return Login(
                sharedPreferences.getInt("id_user", -1),
                sharedPreferences.getString("username", null),
                sharedPreferences.getString("status", null)
            )
        }

    fun saveLogin(login : Login){
        val sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefManager.edit()
        editor.putInt("id_user", login.id_user)
        editor.putString("username", login.username)
        editor.putString("status", login.status)
        editor.apply()
    }

    fun logout(){
        val sharedPrefManager = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefManager.edit()
        editor.clear()
        editor.apply()
    }
    companion object{
        private val SHARED_PREF_NAME = "shared_pref"
        private var mInstances: SharedPrefManager? = null

        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstances == null){
                mInstances =
                    SharedPrefManager(mCtx)
            }
            return mInstances as SharedPrefManager
        }
    }
}