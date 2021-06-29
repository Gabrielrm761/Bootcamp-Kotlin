package com.example.bootcampkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcampkotlin.DetailActivity.Companion.EXTRA_CONTACT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class MainActivity : AppCompatActivity(), ClickItemContactListener {
    private val rvList: RecyclerView by lazy{
        findViewById(R.id.rv_list)
    }

    private val adapter = ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawner_menu)

        initDrawer()
        fetchListContact()
        bindViews()
        //updateList()
    }

    private fun fetchListContact(){
        val list = arrayListOf(
            Contact(
                name = "Gabriel Rocha",
                phone = "(00) 0000-0000",
                photograph = "img.png"
            ),
            Contact(
                name = "Luna Alexandre",
                phone = "(99) 9999-9999",
                photograph = "img.png"
            )
        )
        //faz a conversão de objeto de classe para uma string em json
        getInstanceSharedPreferences().edit {
            val json = Gson().toJson(list)
            putString("contacts", json)
            commit()
        }
    }

    private fun getInstanceSharedPreferences() : SharedPreferences {
        return getSharedPreferences("br.com.bootcampkotlin.PREFERENCES", Context.MODE_PRIVATE)
    }

    private fun initDrawer(){
        val drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.open_drawer,R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun bindViews(){
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)
        updateList()
    }

    private fun getListContacts() : List<Contact>{
        val list = getInstanceSharedPreferences().getString("contacts","[]")
        val turnsType = object : TypeToken<List<Contact>>(){}.type //faz a conversão da string obtido do sharedPreferences para um objeto de classe
        return Gson().fromJson(list, turnsType)
    }

    private fun updateList(){
        val list = getListContacts()
        adapter.updateList(list)
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.item_menu_1 ->{
                showToast("Exibindo item do menu 1")
                true
            }
            R.id.item_menu_2 ->{
                showToast("Exibindo item do menu 2")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun clickItemContact(contact: Contact) {
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra(EXTRA_CONTACT, contact)
        startActivity(intent)
    }
}