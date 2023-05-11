/*package com.example.volunteersmanagement.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteersmanagement.Models.VolunteersModel
import com.example.volunteersmanagement.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Create : AppCompatActivity() {

    private lateinit var name1: EditText
   private lateinit var name2: EditText
   private lateinit var age: EditText
   private lateinit var gender: EditText
   private lateinit var date: EditText
   private lateinit var email: EditText
   private lateinit var phone: EditText
   private lateinit var btnCreate: Button
   private lateinit var button: Button

   private  lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        btnCreate.setOnClickListener{
            val intent = Intent(this, Create::class.java)
            startActivity(intent)
        }


        name1 = findViewById(R.id.name1)
        name2 = findViewById(R.id.name2)
        age = findViewById(R.id.age)
        gender = findViewById(R.id.gender)
        date = findViewById(R.id.date)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        btnCreate= findViewById(R.id.btnCreate)
        button = findViewById(R.id.button)

        dbRef= FirebaseDatabase.getInstance().getReference("Volunteers")

        btnCreate.setOnClickListener{
            saveVolunteersData()
        }

        }
    private fun saveVolunteersData() {
        val fName = name1.text.toString()
        val lName = name2.text.toString()
        val vAge = age.text.toString()
        val vGender = gender.text.toString()
        val vDate = date.text.toString()
        val vEmail = email.text.toString()
        val vPhone = phone.text.toString()

        if(fName.isEmpty()){
            name1.error = "Please Enter fist name"

        }
        if(lName.isEmpty()){
            name2.error = "Please Enter Last name"

        }
        if (vAge.isEmpty()){
            age.error = "please enter age"
        }
        if(vGender.isEmpty()){
            gender.error = "Please enter your gender"
        }
        if(vDate.isEmpty()){
            date.error = "please enter your birthday"

        }
        if(vEmail.isEmpty()){
            email.error = "please enter email"
        }
        if(vPhone.isEmpty()){
            phone.error = "Please emnter contact number"
        }

        val volId =  dbRef.push().key!!

        val volunteers = VolunteersModel(fName, lName, vAge, vGender, vDate, vEmail, vPhone, vPhone)

        dbRef.child(volId).setValue(volunteers).addOnCompleteListener{
            Toast.makeText(this,"Data inserted Successfully",Toast.LENGTH_LONG).show()
        }.addOnFailureListener{ err ->
            Toast.makeText(this,"error ${err.message}",Toast.LENGTH_LONG).show()

        }
    }

}
*/
package com.example.volunteersmanagement.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteersmanagement.Models.VolunteersModel
import com.example.volunteersmanagement.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Create : AppCompatActivity() {

    private lateinit var name1: EditText
    private lateinit var name2: EditText
    private lateinit var age: EditText
    private lateinit var gender: EditText
    private lateinit var date: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var btnCreate: Button
    private lateinit var button: Button

    private lateinit var dbRef: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        name1 = findViewById(R.id.name1)
        name2 = findViewById(R.id.name2)
        age = findViewById(R.id.age)
        gender = findViewById(R.id.gender)
        date = findViewById(R.id.date)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        btnCreate = findViewById(R.id.btnCreate)
        button = findViewById(R.id.button)

        dbRef = FirebaseDatabase.getInstance().getReference("Volunteers")

        btnCreate.setOnClickListener {
            saveVolunteersData()
        }

    }

    private fun saveVolunteersData() {
        val fName = name1.text.toString()
        val lName = name2.text.toString()
        val vAge = age.text.toString()
        val vGender = gender.text.toString()
        val vDate = date.text.toString()
        val vEmail = email.text.toString()
        val vPhone = phone.text.toString()

        if (fName.isEmpty()) {
            name1.error = "Please Enter first name"
            return
        }
        if (lName.isEmpty()) {
            name2.error = "Please Enter Last name"
            return
        }
        if (vAge.isEmpty()) {
            age.error = "please enter age"
            return
        }
        if (vGender.isEmpty()) {
            gender.error = "Please enter your gender"
            return
        }
        if (vDate.isEmpty()) {
            date.error = "please enter your birthday"
            return
        }
        if (vEmail.isEmpty()) {
            email.error = "please enter email"
            return
        }
        if (vPhone.isEmpty()) {
            phone.error = "Please enter contact number"
            return
        }

        val volId = dbRef.push().key!!

        val volunteers = VolunteersModel(fName, lName, vAge, vGender, vDate, vEmail, vPhone, volId)

        dbRef.child(volId).setValue(volunteers).addOnCompleteListener {
            Toast.makeText(this, "Data inserted Successfully", Toast.LENGTH_LONG).show()
            clearFields()
        }.addOnFailureListener { err ->
            Toast.makeText(this, "Error: ${err.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        name1.text.clear()
        name2.text.clear()
        age.text.clear()
        gender.text.clear()
        date.text.clear()
        email.text.clear()
        phone.text.clear()
    }
}

private fun Button.findViewById(function: () -> Unit) {

}







