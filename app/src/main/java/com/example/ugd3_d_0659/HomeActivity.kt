package com.example.ugd3_d_0659

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.DialogInterface
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        setTitle("Bisa Belajar")

        changeFragment(FragmentHome())

        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menu_home -> {
                    changeFragment(FragmentHome())
                    true
                }
                R.id.menu_mata_pelajaran -> {
                    changeFragment(FragmentMataPelajaran())
                    true
                }
                else -> false
            }
        }
    }

    fun changeFragment(fragment: Fragment?){
        if(fragment !=null){
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmen, fragment)
                .commit()
        }
    }
/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //disini kita menghubungkan menu yang telah kita buat dengan activity ini
        val manuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_home){
            //jika menu yang dipilih adalah Mahasiswa maka ganti fragmentnya dengan fragment mahasiswa
            changeFragment(FragmentHome())
        }else if(item.itemId == R.id.menu_mata_pelajaran){
            changeFragment(FragmentMataPelajaran())
        }
        return super.onOptionsItemSelected(item)
    }*/
}