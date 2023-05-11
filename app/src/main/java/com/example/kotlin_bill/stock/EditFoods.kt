package com.nsb.myproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.nsb.myproject.model.FoodModel

class EditFoods : AppCompatActivity() {
    private lateinit var tvEditFoodName : TextView
    private lateinit var tvEditFoodQty : TextView
    private lateinit var tvEditFoodExQty : TextView
    private lateinit var tvTitle : TextView
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_foods)

        initView()
        setValues()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("foodId").toString(),
                intent.getStringExtra("foodName").toString()
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("foodId").toString()
            )
        }
    }

    private fun initView() {
        tvEditFoodName = findViewById(R.id.edit_name)
        tvEditFoodQty = findViewById(R.id.edit_qty)
        tvEditFoodExQty = findViewById(R.id.edit_ex_aty)
        tvTitle = findViewById(R.id.edit_food_topic)
        btnDelete = findViewById(R.id.btn_delete)
        btnUpdate = findViewById(R.id.btn_update)
    }

    private fun setValues() {
        tvEditFoodName.text = intent.getStringExtra("foodName")
        tvEditFoodQty.text = intent.getStringExtra("foodQty")
        tvEditFoodExQty.text = intent.getStringExtra("foodExQty")
        tvTitle.text = intent.getStringExtra("foodName")
    }

    private fun openUpdateDialog(foodId : String, foodName : String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_food_update_dialog_box, null)

        mDialog.setView(mDialogView)

        val etFoodName = mDialogView.findViewById<EditText>(R.id.etUpdateFoodName)
        val etFoodQty = mDialogView.findViewById<EditText>(R.id.etUpdateFoodQty)
        val etFoodExQty = mDialogView.findViewById<EditText>(R.id.etUpdateFoodExpQty)
        val btnFoodUpdate = mDialogView.findViewById<Button>(R.id.btnUpdateFood)

        etFoodName.setText(intent.getStringExtra("foodName").toString())
        etFoodQty.setText(intent.getStringExtra("foodQty").toString())
        etFoodExQty.setText(intent.getStringExtra("foodExQty").toString())

        mDialog.setTitle("Updating $foodName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnFoodUpdate.setOnClickListener {
            updateFoodData(
                foodId,
                etFoodName.text.toString(),
                etFoodQty.text.toString(),
                etFoodExQty.text.toString(),
            )

            Toast.makeText(applicationContext, "Food Updated", Toast.LENGTH_LONG).show()

            //Setting Updated Data to TextViews
            tvEditFoodName.text = etFoodName.text.toString()
            tvEditFoodQty.text = etFoodQty.text.toString()
            tvEditFoodExQty.text = etFoodExQty.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateFoodData(id : String, name : String, qty : String, expQty : String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Foods").child(id)
        val foodInfo = FoodModel(id, name, qty, expQty)
        dbRef.setValue(foodInfo)
    }

    private fun deleteRecord(id : String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Foods").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Food Data Deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, ViewFood::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {error ->
            Toast.makeText(this, "Deleting Error : ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

}