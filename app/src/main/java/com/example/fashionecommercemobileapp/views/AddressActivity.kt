package com.example.fashionecommercemobileapp.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.adapters.AddressAdapter
import com.example.fashionecommercemobileapp.model.Address
import com.example.fashionecommercemobileapp.retrofit.repository.AddressRepository
import com.example.fashionecommercemobileapp.viewmodels.AddressViewModel
import kotlinx.android.synthetic.main.activity_address.*

class AddressActivity : AppCompatActivity() {
    private var addressViewModel: AddressViewModel? = null
    private lateinit var addressAdapter: AddressAdapter
    private var isCheckOut: Boolean = false
    private lateinit var isSelected: LiveData<Boolean>

    var idAccount : String = ""
    private lateinit var addressAdapter: AddressAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        val intent: Intent = intent
        isCheckOut = intent.getBooleanExtra("isCheckOut", false)

        setUpAddressRecyclerView()
        val spf = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val idAccount = spf.getString("Id", null)

        AddressRepository.setContext(this@AddressActivity)
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel::class.java)
        addressViewModel!!.init()
        if (idAccount != null) {
            addressViewModel!!.getAddressData(idAccount)
                ?.observe(this, Observer { retrieveList(it as ArrayList<Address>) })
        }


        addressViewModel!!.getAddressData(idAccount.toString())?.observe(this, Observer { setUpAddressRecyclerView(it) })
        //add address
        add_address_button.setOnClickListener {
            val intent = Intent(this, AddAddressActivity::class.java).apply { }
                intent.putExtra("idAccount",idAccount)
            (this as Activity).startActivityForResult(intent, 0)

            addressAdapter.notifyDataSetChanged()
        }
    }

    private fun setUpAddressRecyclerView(listAddress: List<Address>) {
        addressRecyclerView = findViewById(R.id.address_recycler)
        addressAdapter = AddressAdapter(this, listAddress.toMutableList(), addressViewModel, idAccount)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false)
        addressRecyclerView.layoutManager = layoutManager
        addressRecyclerView.adapter = addressAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        addressViewModel!!.getAddressData(idAccount.toString())?.observe(this, Observer { setUpAddressRecyclerView(it) })
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
        isSelected = addressAdapter.getSate()
        val observer = Observer<Boolean> { it ->
            if (it) {
                onBackPressed()
            }
        }
        isSelected.observe(this, observer)

    }

    private fun setUpAddressRecyclerView() {
        addressAdapter = AddressAdapter(this, arrayListOf(), isCheckOut)
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
        address_recycler.layoutManager = layoutManager
        address_recycler.adapter = addressAdapter
    }

    private fun retrieveList(listAddress: ArrayList<Address>) {
        addressAdapter.changeData(listAddress)
    }

    override fun onBackPressed() {
        val intent: Intent = Intent()
        val address: MutableLiveData<Address> = addressAdapter.getAddress()
        intent.putExtra("idAddress", address.value?.idAddress.toString())
        intent.putExtra("name", address.value?.name)
        intent.putExtra("address", address.value?.address)
        intent.putExtra("phoneNumber", address.value?.phoneNumber)
        setResult(RESULT_OK, intent)
        finish()
    }
}