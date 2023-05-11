package com.example.kotlin_bill.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kotlin_bill.R
import com.example.kotlin_bill.models.FoodModel
import com.google.firebase.database.FirebaseDatabase

class FoodDetailsActivity : AppCompatActivity() {

    private lateinit var tvDonateId: TextView
    private lateinit var tvDonateName: TextView
    private lateinit var tvDonateDate: TextView
    private lateinit var tvDonateTime: TextView
    private lateinit var tvDonateFood: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("donateId").toString(),
                intent.getStringExtra("donateName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("donateId").toString()
            )
        }

    }

    //delete function
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("shareFood").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Food data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FoodFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }





    private fun initView() {
        tvDonateId = findViewById(R.id.tvDonateId)
        tvDonateName = findViewById(R.id.tvDonateName)
        tvDonateDate = findViewById(R.id.tvDonateDate)
        tvDonateTime = findViewById(R.id.tvDonateTime)
        tvDonateFood = findViewById(R.id.tvDonateFood)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        //passing data
        tvDonateId.text = intent.getStringExtra("donateId")
        tvDonateName.text = intent.getStringExtra("donateName")
        tvDonateDate.text = intent.getStringExtra("donateDate")
        tvDonateTime.text = intent.getStringExtra("donateTime")
        tvDonateFood.text = intent.getStringExtra("donateFood")

    }

    private fun openUpdateDialog(
        donateId: String,
        donateName: String

    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.food_update_dialog, null)

        mDialog.setView(mDialogView)

        val etDonateName = mDialogView.findViewById<EditText>(R.id.etDonateName)
        val etDonateDate = mDialogView.findViewById<EditText>(R.id.etDonateDate)
        val etDonateTime = mDialogView.findViewById<EditText>(R.id.etDonateTime)
        val etDonateFood = mDialogView.findViewById<EditText>(R.id.etDonateFood)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        //update
        etDonateName.setText(intent.getStringExtra("donateName").toString())
        etDonateDate.setText(intent.getStringExtra("donateDate").toString())
        etDonateTime.setText(intent.getStringExtra("donateTime").toString())
        etDonateFood.setText(intent.getStringExtra("donateFood").toString())

        mDialog.setTitle("Updating $donateName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateDonateData(
                donateId,
                etDonateName.text.toString(),
                etDonateDate.text.toString(),
                etDonateTime.text.toString(),
                etDonateFood.text.toString()
            )
            //updates msg
            Toast.makeText(applicationContext, "Food Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvDonateName.text = etDonateName.text.toString()
            tvDonateDate.text = etDonateDate.text.toString()
            tvDonateTime.text = etDonateTime.text.toString()
            tvDonateFood.text = etDonateFood.text.toString()

            alertDialog.dismiss()

        }

    }

    private fun updateDonateData(
        id: String,
        name: String,
        date: String,
        time: String,
        food: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("shareFood").child(id)
        val donateInfo = FoodModel(id, name, date, time, food)
        dbRef.setValue(donateInfo)
    }
}