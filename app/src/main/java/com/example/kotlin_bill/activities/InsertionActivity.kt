package com.example.kotlin_bill.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.kotlin_bill.models.EmployeeModel
import com.example.kotlin_bill.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {
    //initializing variables

    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpSalary: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpAge = findViewById(R.id.etEmpAge)
        etEmpSalary = findViewById(R.id.etEmpSalary)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("BankDB")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }

    }

    private fun saveEmployeeData() {

        //Geting Values
        val empName = etEmpName.text.toString()
        val empAge = etEmpAge.text.toString()
        val empSalary = etEmpSalary.text.toString()

        //validation
        if (empName.isEmpty()) {
            etEmpName.error = "Please enter name"
        }
        if (empAge.isEmpty()) {
            etEmpAge.error = "Please enter age"
        }
        if (empSalary.isEmpty()) {
            etEmpSalary.error = "Please enter salary"
        }

        //genrate unique ID
        val empId = dbRef.push().key!!

        val employee = EmployeeModel(empId, empName, empAge, empSalary)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this,"data insert successfully",Toast.LENGTH_SHORT).show()

                //clear data after insert
                etEmpName.text.clear()
                etEmpAge.text.clear()
                etEmpSalary.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_SHORT).show()
            }

    }

}