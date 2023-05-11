package com.nsb.myproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StockDashboard : AppCompatActivity() {

    private lateinit var btnAddFood: Button
    private lateinit var btnViewFood: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_dashboard)

        btnAddFood = findViewById(R.id.btn_add)
        btnViewFood = findViewById(R.id.btn_view)

        btnAddFood.setOnClickListener {
            val intent = Intent(this, AddFood::class.java)
            startActivity(intent)
        }

        btnViewFood.setOnClickListener {
            val intent = Intent(this, ViewFood::class.java)
            startActivity(intent)
        }

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()
    }
}