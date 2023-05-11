package com.nsb.myproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nsb.myproject.model.FoodModel

class AddFood : AppCompatActivity() {
    private lateinit var etFoodName : EditText
    private lateinit var etQty : EditText
    private lateinit var etExQty : EditText
    private lateinit var btnAddFood : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        etFoodName = findViewById(R.id.edit_food_name)
        etQty = findViewById(R.id.add_food_quantity)
        etExQty = findViewById(R.id.add_food_expected_quantity)
        btnAddFood = findViewById(R.id.btn_update)

        dbRef = FirebaseDatabase.getInstance().getReference("Foods")

        btnAddFood.setOnClickListener {
            saveFoodData()
        }
    }

    private fun saveFoodData() {
        //Getting Values
        val foodName = etFoodName.text.toString()
        val foodQty = etQty.text.toString()
        val foodExQty = etExQty.text.toString()

        //Validations
        if (foodName.isEmpty()) {
            etFoodName.error = "Please Input Food Name"
        }

        if (foodQty.isEmpty()) {
            etQty.error = "Please Input Quantity"
        }

        if (foodExQty.isEmpty()) {
            etExQty.error = "Please Input Expected Quantity"
        }

        val foodId = dbRef.push().key!!

        val food = FoodModel(foodId, foodName, foodQty, foodExQty)

        dbRef.child(foodId).setValue(food)
            .addOnCompleteListener {
                Toast.makeText(this, "Food Added Successfully", Toast.LENGTH_LONG).show()
                etFoodName.text.clear()
                etQty.text.clear()
                etExQty.text.clear()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}