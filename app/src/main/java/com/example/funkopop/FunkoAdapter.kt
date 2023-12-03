package com.example.funkopop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FunkoAdapter(val activity: MainActivity, var funkoList: List<Funko>) :
    RecyclerView.Adapter<FunkoAdapter.FunkoHolder>() {

        inner class FunkoHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
            var nameTextView = view.findViewById<TextView>(R.id.holder_name_text_view)
            var groupTextView = view.findViewById<TextView>(R.id.holder_group_text_view)
            var numberTextView = view.findViewById<TextView>(R.id.holder_funko_number_text_view)

            init {
                view.isClickable = true
                view.setOnClickListener(this)
            }

            override fun onClick(p0: View?) {
                activity.showFunko(adapterPosition)
            }


        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunkoHolder {
        val itemView = LayoutInflater.from(this.activity).inflate(R.layout.holder_funko,
            parent, false)
        return FunkoHolder(itemView)
    }

    override fun onBindViewHolder(holder: FunkoHolder, position: Int) {
        val currentFunko = this.funkoList[position]
        holder.nameTextView.text = currentFunko.name
        holder.groupTextView.text = currentFunko.group
        holder.numberTextView.text = currentFunko.number.toString()
    }

    override fun getItemCount(): Int {
        return this.funkoList.size
    }

    public fun filterList(newFunkoList: List<Funko>) {
        this.funkoList = newFunkoList
        notifyDataSetChanged()
    }
}