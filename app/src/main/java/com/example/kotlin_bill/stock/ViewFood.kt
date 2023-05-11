package com.nsb.myproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.nsb.myproject.adapter.FoodAdapter
import com.nsb.myproject.model.FoodModel

class ViewFood : AppCompatActivity() {
    private lateinit var iconAddFood : ImageView
    private lateinit var rvFood : RecyclerView
    private lateinit var tvLoading : TextView
    private lateinit var foodList : ArrayList<FoodModel>

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_food)

        iconAddFood = findViewById(R.id.icon_add_food)
        rvFood = findViewById(R.id.rvFood)
        rvFood.layoutManager = LinearLayoutManager(this)
        rvFood.setHasFixedSize(true)
        tvLoading = findViewById(R.id.tvLoading)

        iconAddFood.setOnClickListener {
            val intent = Intent(this, AddFood::class.java)
            startActivity(intent)
        }

        foodList = arrayListOf<FoodModel>()
        getFoodData()
    }

    private fun getFoodData() {
        rvFood.visibility = View.GONE
        tvLoading.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Foods")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                foodList.clear()
                if (snapshot.exists()) {
                    for (foodSnap in snapshot.children) {
                        val foodData = foodSnap.getValue(FoodModel::class.java)
                        foodList.add(foodData!!)
                    }

                    val mAdapter = FoodAdapter(foodList)
                    rvFood.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : FoodAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@ViewFood, EditFoods::class.java)

                            //Put Extra
                            intent.putExtra("foodId", foodList[position].foodId)
                            intent.putExtra("foodName", foodList[position].foodName)
                            intent.putExtra("foodQty", foodList[position].foodQty)
                            intent.putExtra("foodExQty", foodList[position].foodExQty)

                            startActivity(intent)
                        }
                    })

                    rvFood.visibility = View.VISIBLE
                    tvLoading.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}