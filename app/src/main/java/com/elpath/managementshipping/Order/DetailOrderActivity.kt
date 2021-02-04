package com.elpath.managementshipping.Order

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.elpath.managementshipping.Network.RetrofitClient
import com.elpath.managementshipping.R
import kotlinx.android.synthetic.main.activity_detail_order.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.list_order.view.*
import kotlinx.coroutines.NonCancellable.cancel
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class DetailOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_order)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        val order = intent.getParcelableExtra("order") as Order

        when (order.kurir) {
            "gojek" -> {
                imgKurir_det.setImageResource(R.drawable.gojek)
            }
            "grab" -> {
                imgKurir_det.setImageResource(R.drawable.grab)
            }
            else -> {
                imgKurir_det.setImageResource(R.drawable.jnt)
            }
        }

        status_det.text =order.status
        nama_brang_detail.text = order.nama_barang
        jml_brg_detail.text = order.jumlah_barang
        nama_pembeli_det.text = order.nama_pembeli
        alamat_det.text = order.alamat
        kurir_det.text = order.kurir
        kode_booking_det.text = order.kode_kirim

        val id = order.id_order
        val stts = "Terkirim"

        btn_status.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Yakiiiinn?")
            builder.setMessage("Serius sudah dikirim?")

            builder.setPositiveButton(android.R.string.yes){ dialog, which ->
                pb_detail.visibility = View.VISIBLE
                btn_status.isEnabled = false
                RetrofitClient.instances.konfirmasiOrder(id, stts)
                    .enqueue(object : retrofit2.Callback<ResponseReq> {
                        override fun onFailure(call: Call<ResponseReq>, t: Throwable) {
                            Toast.makeText(this@DetailOrderActivity, t.message, Toast.LENGTH_LONG).show()
                            pb_detail.visibility = View.GONE
                            btn_status.isEnabled = true
                        }
                        override fun onResponse(call: Call<ResponseReq>, response: Response<ResponseReq>) {
                            if (response.body()?.error!!){
                                Toast.makeText(this@DetailOrderActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                                pb_detail.visibility = View.GONE
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(this@DetailOrderActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                                pb_detail.visibility = View.GONE
                                btn_status.isEnabled = true
                            }
                        }
                    })
            }
            builder.setNegativeButton(android.R.string.no){ dialog, which ->
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
            }
            builder.show()

        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}