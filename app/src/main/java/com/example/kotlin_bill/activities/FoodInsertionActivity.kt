package com.example.kotlin_bill.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.kotlin_bill.models.FoodModel
import com.example.kotlin_bill.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FoodInsertionActivity : AppCompatActivity() {
    //initializing variables

    private lateinit var etDonateName: EditText
    private lateinit var etDonateDate: EditText
    private lateinit var etDonateTime: EditText
    private lateinit var etDonateFood: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_insertion)

        etDonateName = findViewById(R.id.etDonateName)
        etDonateDate = findViewById(R.id.etDonateDate)
        etDonateTime = findViewById(R.id.etDonateTime)
        etDonateFood = findViewById(R.id.etDonateFood)
        btnSaveData = findViewById(R.id.btnSave)
//database initialize
        dbRef = FirebaseDatabase.getInstance().getReference("shareFood")

        btnSaveData.setOnClickListener {
            saveDonateData()
        }

    }

    private fun saveDonateData() {

        //Getting Values
        val donateName = etDonateName.text.toString()
        val donateDate = etDonateDate.text.toString()
        val donateTime = etDonateTime.text.toString()
        val donateFood = etDonateFood.text.toString()

        //validation
        if(donateName.isEmpty() || donateDate.isEmpty() || donateTime.isEmpty() || donateFood.isEmpty()){
        if (donateName.isEmpty()) {
            etDonateName.error = "Please enter Name"
        }
        if (donateDate.isEmpty()) {
            etDonateDate.error = "Please enter Date"
        }
        if (donateTime.isEmpty()) {
            etDonateTime.error = "Please enter Time"
        }
        if (donateFood.isEmpty()) {
            etDonateFood.error = "Please enter Food"
        }
            Toast.makeText(this, "Some areas are not filled", Toast.LENGTH_LONG).show()
        }
        else{

        //generate unique ID
        val donateId = dbRef.push().key!!

        val card = FoodModel(donateId, donateName, donateDate, donateTime, donateFood)

        dbRef.child(donateId).setValue(card)
            .addOnCompleteListener {
                Toast.makeText(this,"data insert successfully",Toast.LENGTH_SHORT).show()

                //clear data after insert
                etDonateName.text.clear()
                etDonateDate.text.clear()
                etDonateTime.text.clear()
                etDonateFood.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_SHORT).show()
            }

    }

}}