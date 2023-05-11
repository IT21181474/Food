package com.example.volunteersmanagement.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteersmanagement.Models.VolunteersModel
import com.example.volunteersmanagement.R
import com.google.firebase.database.FirebaseDatabase

class VolunteersDetails : AppCompatActivity(){
     private lateinit var tvVolfName: TextView
    private lateinit var tvVollName: TextView
    private lateinit var tvVolAge: TextView
    private lateinit var tvVolGender: TextView
    private lateinit var tvVolDate: TextView
    private lateinit var tvVolEmail: TextView
    private lateinit var tvVolPhone: TextView
    private lateinit var tvUpdate: Button
    private lateinit var tvDelete: Button


    override fun onCreate(savedInstanceState: Bundle?, ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)

        initView()
        setValuesToView()


        tvUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("fName").toString(),
                intent.getStringExtra("lName").toString(),
                intent.getStringExtra("volAge").toString(),
                intent.getStringExtra("volGender").toString(),
                intent.getStringExtra("volDate").toString(),
                intent.getStringExtra("volEmail").toString(),
                intent.getStringExtra("volPhone").toString()



            )
        }
        tvDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("volId").toString()
            )
        }
    }
    private fun deleteRecord(
        id: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Volunteers").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Volunteers Data Deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, ViewProfile::class.java)
            finish()
            startActivity(intent)

        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting err ${error.message}", Toast.LENGTH_LONG ).show()

        }
    }

    private fun openUpdateDialog(volId: String, fName: String, lName: String, volAge: String, volGender: String, volDate: String, volEmail: String) {

    }

    private fun initView() {

    }
    private fun setValuesToView(){

        tvVolfName.text = intent.getStringExtra("fName")
        tvVollName.text = intent.getStringExtra("lName")
        tvVolAge.text = intent.getStringExtra("volAge")
        tvVolGender.text = intent.getStringExtra("volGender")
        tvVolDate.text = intent.getStringExtra("volDate")
        tvVolEmail.text = intent.getStringExtra("volEmail")
        tvVolPhone.text = intent.getStringExtra("volPhone")

    }
    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(
        volId: String,
        fName: String,
        lName: String,
        volAge: String,
        volGender: String,
        volDate: String,
        volEmail: String,
        volPhone: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update, null)

        mDialog.setView(mDialogView)

        val name1 = mDialogView.findViewById<EditText>(R.id.name1)
        val name2 = mDialogView.findViewById<EditText>(R.id.name2)
        val age = mDialogView.findViewById<EditText>(R.id.age)
        val gender = mDialogView.findViewById<EditText>(R.id.gender)
        val date = mDialogView.findViewById<EditText>(R.id.date)
        val email = mDialogView.findViewById<EditText>(R.id.email)
        val phone = mDialogView.findViewById<EditText>(R.id.phone)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        name1.setText(intent.getStringExtra("fName").toString())
        name2.setText(intent.getStringExtra("lName").toString())
        age.setText(intent.getStringExtra("volAge").toString())
        gender.setText(intent.getStringExtra("volGender").toString())
        date.setText(intent.getStringExtra("volDate").toString())
        email.setText(intent.getStringExtra("volPhone").toString())
        phone.setText(intent.getStringExtra("volPhone").toString())

        mDialog.setTitle("Updating $fName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updtaeVolData(
                volId,
                name1.text.toString(),
                name2.text.toString(),
                age.text.toString(),
                gender.text.toString(),
                date.text.toString(),
                email.text.toString(),
                phone.text.toString()

            )
            Toast.makeText(applicationContext, "Volunteers Data Updated", Toast.LENGTH_LONG).show()

            tvVolfName.text = name1.text.toString()
            tvVollName.text = name2.text.toString()
            tvVolAge.text =  age.text.toString()
            tvVolGender.text = gender.text.toString()
            tvVolDate.text =  date.text.toString()
            tvVolEmail.text = email.text.toString()
            tvVolPhone.text = phone.text.toString()


            alertDialog.dismiss()

        }


    }
    private fun updtaeVolData(
        id: String,
        fName: String,
        lName: String,
        vAge: String,
        vGender: String,
        vDate: String,
        vEmail: String,
        vPhone: String
    ) {
    val dbRef = FirebaseDatabase.getInstance().getReference("Volunteers Manage").child(id)
        val volInfo = VolunteersModel(id,fName,lName,vAge,vGender,vDate,vEmail,vPhone)
        dbRef.setValue(volInfo)

    }
}