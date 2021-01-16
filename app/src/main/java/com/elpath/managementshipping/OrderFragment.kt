package com.elpath.managementshipping

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.elpath.managementshipping.Network.NoConnectionActivity
import com.elpath.managementshipping.Network.RetrofitClient
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            loadOrder()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipeRefreshOrder.setOnRefreshListener {
            if (isConnected()){
                viewLifecycleOwner.lifecycleScope.launch {
                    loadOrder()
                    Log.e(tag, "Coroutine Reload")
                }
            }else{
                val intent = Intent(this.activity, NoConnectionActivity::class.java)
                startActivity(intent)
            }
            swipeRefreshOrder.isRefreshing = false
        }
    }



    private suspend fun loadOrder(){
        val response = RetrofitClient.instances.orders()
        val value = response.body()?.value
        if (value!! == "1"){
            response.body()?.response?.let { showOrder(it) }
            pbOrder.visibility = View.GONE
        }else{
            Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show()
            pbOrder.visibility = View.GONE
        }
    }

    fun showOrder(order: ArrayList<Order>){
        rvOrder.layoutManager = LinearLayoutManager(this.activity)
        rvOrder.adapter = order?.let {OrderAdapter(it) }
        rvOrder.setHasFixedSize(true)
        rvOrder.adapter?.notifyDataSetChanged()
    }
    @SuppressLint("NewApi")
    private  fun  isConnected(): Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}