package com.alfianguide.kotlineverywheresemarang2019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainActivity : AppCompatActivity() {

    val listManusia = mutableListOf<ManusiaContract>()
    lateinit var manusiaAdapter: ManusiaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manusiaAdapter = ManusiaAdapter(listManusia, object : onClick {
            override fun showToast(manusiaContract: ManusiaContract) =
                Toast.makeText(this@MainActivity, manusiaContract.nama, Toast.LENGTH_SHORT).show()

            override fun update(manusiaContract: ManusiaContract) =
                updateData(manusiaContract)
        })
        main_recyclerview.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = manusiaAdapter
        }
        main_btn_save.onClick {
            saveData()
        }
        main_btn_refresh.onClick {
            readData()
        }

    }

    private fun updateData(manusiaContract: ManusiaContract) {
        database.use {
            update(
                ManusiaContract.TABLE_HUMAN,
                ManusiaContract.NAME to manusiaContract.nama
            )
            .whereArgs("$ManusiaContract.ID = {${manusiaContract.id}}")
            .exec()
        }
    }

    private fun readData() {
        var list: List<ManusiaContract> = mutableListOf()
        list = readDb(list)
        Log.d("List", list.toString())
        listManusia.addAll(list)
        manusiaAdapter.notifyDataSetChanged()
    }

    private fun readDb(list: List<ManusiaContract>): List<ManusiaContract> {
        var list1 = list
        database.use {
            val result = select(ManusiaContract.TABLE_HUMAN)
            list1 = result.parseList(classParser())
        }
        return list1
    }

    private fun saveData() {
        database.use {
            insert(
                ManusiaContract.TABLE_HUMAN,
                ManusiaContract.NAME
                        to main_edt_name.text.toString(),
                ManusiaContract.ADDRESS
                        to main_edt_address.text.toString()
            )
        }
    }
}
