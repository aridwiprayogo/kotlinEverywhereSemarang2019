package com.alfianguide.kotlineverywheresemarang2019

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_manusia.view.*

interface onClick {
    fun showToast(manusiaContract: ManusiaContract)
    fun update(manusiaContract: ManusiaContract)
}

class ManusiaAdapter(val listManusia: List<ManusiaContract>, val onclick: onClick)
    :RecyclerView.Adapter<ManusiaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.card_manusia,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = listManusia.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listManusia[position],onclick)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view){
        fun onBind(manusiaContract: ManusiaContract, onclick: onClick){
            val a = with(view){
                tv_nama.text = manusiaContract.nama
                tv_alamat.text = manusiaContract.address
                this.setOnClickListener {
                    onclick.showToast(manusiaContract)
                }
            }
        }
    }
}