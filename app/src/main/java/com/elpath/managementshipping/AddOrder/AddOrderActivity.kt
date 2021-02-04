package com.elpath.managementshipping.AddOrder

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.elpath.managementshipping.Network.RetrofitClient
import com.elpath.managementshipping.Order.ResponseReq
import com.elpath.managementshipping.R
import com.elpath.managementshipping.SharedPrefManager.SharedPrefManager
import kotlinx.android.synthetic.main.activity_add_order.*
import kotlinx.android.synthetic.main.activity_detail_order.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddOrderActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)

        actionBar?.title  = "Add Order"

        val user = SharedPrefManager.getInstance(this).login.status
        if (user.equals("front")){
            btnAddd.isEnabled = false
        }

        val jml =  edtJmlAdd.text.toString()
        var jmli = jml.toInt()
        btnPlus.setOnClickListener{
            jmli++
            edtJmlAdd.text = jmli.toString()
        }
        btnMin.setOnClickListener{
            if (jmli > 1)
                jmli--
            edtJmlAdd.text = jmli.toString()
        }


        val spinner = findViewById<Spinner>(R.id.spnKurir)
        ArrayAdapter.createFromResource(
            this,
            R.array.kurir,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        btnAddd.setOnClickListener {
            val barang = edtBarangAdd.text.toString()
            val jmlah = edtJmlAdd.text.toString()
            val penerima = edtPenerimaAdd.text.toString()
            val alamat = edtAlamatAdd.text.toString()
            val kurir = spinner.selectedItem.toString()
            val kode = edtKodeKirimAdd.text.toString()


            val builder = AlertDialog.Builder(this)
            builder.setTitle("Verifikasi")
            builder.setMessage("apakah data Orderan sudah benar?")
            builder.setPositiveButton(android.R.string.yes){ dialog, which ->
                pbAdd.visibility = View.VISIBLE
                btnAddd.isEnabled = false
                RetrofitClient.instances.addOrder(barang,jmlah,penerima,kurir,kode,alamat).enqueue(object : Callback<ResponseReq>{
                    override fun onFailure(call: Call<ResponseReq>, t: Throwable) {
                        Toast.makeText(this@AddOrderActivity, t.message, Toast.LENGTH_LONG).show()
                        pbAdd.visibility = View.GONE
                    }

                    override fun onResponse(call: Call<ResponseReq>, response: Response<ResponseReq>) {
                        if (response.body()?.value == "1"){
                            Toast.makeText(this@AddOrderActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            pbAdd.visibility = View.GONE
                        }else{
                            Toast.makeText(this@AddOrderActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                            pbAdd.visibility = View.GONE
                            btnAddd.isEnabled = true
                        }
                    }
                })
                }
            builder.setNegativeButton(android.R.string.no){dialog, which ->
                dialog.cancel()
            }
            builder.show()
            }
        }

    }
