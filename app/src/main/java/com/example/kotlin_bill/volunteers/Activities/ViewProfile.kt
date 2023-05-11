package com.example.volunteersmanagement.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteersmanagement.Adapters.VolAdapter
import com.example.volunteersmanagement.Models.VolunteersModel
import com.example.volunteersmanagement.R
import com.google.firebase.database.*

class ViewProfile : AppCompatActivity() {

    private lateinit var volRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var volList: ArrayList<VolunteersModel>
    private lateinit var dbRef: DatabaseReference
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)

        volRecyclerView = findViewById(R.id.rvVol)
        volRecyclerView.setHasFixedSize(true)
        volRecyclerView.layoutManager = LinearLayoutManager(this)

    volList = arrayListOf<VolunteersModel>()

        getVolunteersData()

    }
    private fun getVolunteersData(){
        volRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Volunteers")

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                volList.clear()
                if (snapshot.exists()){
                    for (volSnap in snapshot.children){
                        val volData = volSnap.getValue(VolunteersModel::class.java)
                        volList.add(volData!!)
                    }
                    val mAdapter = VolAdapter(volList)
                    volRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : VolAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val  intent = Intent(this@ViewProfile, VolunteersDetails::class.java )

                            intent.putExtra("fName",volList[position].fName)
                            intent.putExtra("lName",volList[position].lName)
                            intent.putExtra("vAge",volList[position].vAge)
                            intent.putExtra("vGender",volList[position].vGender)
                            intent.putExtra("vDate",volList[position].vDate)
                            intent.putExtra("vEmail",volList[position].vEmail)
                            intent.putExtra("vPhone",volList[position].vPhone)
                            startActivity(intent)

                        }

                    })


                    volRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
