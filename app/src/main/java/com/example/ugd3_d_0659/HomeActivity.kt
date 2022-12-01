package com.example.ugd3_d_0659

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ugd3_d_0659.maps.MapsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)
        title = "Bisa Belajar"

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
                R.id.menu_buku -> {
                    val intent = Intent(this, MenuBuku::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_maps ->{
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_profil -> {
                    changeFragment(FragmentProfil())
                    true
                }
                else -> false
            }
        }
        val intent: Intent = intent
        val bundle = intent.getBundleExtra("login_success")
        val mFragmentHome: Fragment = FragmentHome()
        mFragmentHome.arguments = bundle
        changeFragment(mFragmentHome)


    }

    fun changeFragment(fragment: Fragment?){
        if(fragment !=null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmen, fragment)
                .commit()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //disini kita menghubungkan menu yang telah kita buat dengan activity ini
        val manuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.logout_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_log_out) {
            //jika menu yang dipilih adalah Mahasiswa maka ganti fragmentnya dengan fragment mahasiswa
            val builder: AlertDialog.Builder = AlertDialog.Builder(this@HomeActivity)
            builder.setMessage("Apakah kamu yakin ingin LogOut?")
                .setPositiveButton("YES ", object : DialogInterface.OnClickListener {
                    override fun onClick(dialogInterface: DialogInterface, i: Int) {
                        //keluar dari aplikasi
                        finishAndRemoveTask()
                    }
                })
                .show()
        }else{
            val intent = Intent(this, MenuNote::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}