package com.nsb.myproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_bill.R
import com.nsb.myproject.model.FoodModel

class FoodAdapter (private val foodList: ArrayList<FoodModel>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    private lateinit var mListener : onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_one_food, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFd = foodList[position]
        holder.tvFoodName.text = currentFd.foodName
        holder.tvFoodQty.text = currentFd.foodQty
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val tvFoodName : TextView = itemView.findViewById(R.id.food_title)
        val tvFoodQty : TextView = itemView.findViewById(R.id.food_cal)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}