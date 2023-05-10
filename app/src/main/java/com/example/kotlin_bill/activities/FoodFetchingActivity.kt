package com.example.kotlin_bill.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_bill.R
import com.example.kotlin_bill.adapters.FoodAdapter
import com.example.kotlin_bill.models.FoodModel
import com.google.firebase.database.*

class FoodFetchingActivity : AppCompatActivity() {

    private lateinit var empRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var donateList: ArrayList<FoodModel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_fetching)

        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        donateList = arrayListOf<FoodModel>()

        getDonateData()


    }

    private fun getDonateData() {

        empRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("FoodDB")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               donateList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(FoodModel::class.java)
                        donateList.add(empData!!)
                    }
                    val mAdapter = FoodAdapter(donateList)
                    empRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : FoodAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FoodFetchingActivity, FoodDetailsActivity::class.java)

                            //put extra(passing data to another activity)
                            intent.putExtra("donateId", donateList[position].donateId)
                            intent.putExtra("donateName", donateList[position].donateName)
                            intent.putExtra("donateDate", donateList[position].donateDate)
                            intent.putExtra("donateTime", donateList[position].donateTime)
                            intent.putExtra("donateFood", donateList[position].donateFood)
                            startActivity(intent)
                        }

                    })

                    empRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}