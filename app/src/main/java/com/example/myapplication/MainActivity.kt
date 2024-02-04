package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.myapplication.model.Data
import com.example.myapplication.model.Reqres

class MainActivity : AppCompatActivity() {

    private  val dataList: MutableList<Data> = mutableListOf()
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //setup Adpater

        myAdapter = MyAdapter(dataList)


        // setup Recycler View
        val recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))
        recyclerView.adapter = myAdapter


        // setup Android Networking
        AndroidNetworking.initialize(this)
        AndroidNetworking.get("https://reqres.in/api/users?page=2")
            .build()
            .getAsObject(Reqres::class.java, object : ParsedRequestListener<Reqres>{

                override fun onResponse(response: Reqres) {
                    dataList.addAll(response.data)
                    myAdapter.notifyDataSetChanged()
                }

                override fun onError(anError: ANError?) {

                }
            })
    }
}