package com.geinzTienda.tiendasgeinzadmin

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiendasgeinzadmin.Fragmentos.promociones_fr
import com.example.tiendasgeinzadmin.Fragmentos.servicios_fr
import com.geinzTienda.tiendasgeinzadmin.Fragmentos.articulos_fr
import com.geinzTienda.tiendasgeinzadmin.Fragmentos.noticias_fr
import com.geinzTienda.tiendasgeinzadmin.Fragmentos.pedidos_fr
import com.geinzTienda.tiendasgeinzadmin.Fragmentos.perfil_fr
import com.geinzTienda.tiendasgeinzadmin.adapter.adapterViewPager
import com.geinzTienda.tiendasgeinzadmin.databinding.ActivityPrincipalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Principal : AppCompatActivity() {
    private lateinit var binding:ActivityPrincipalBinding
    private lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPrincipalBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth=FirebaseAuth.getInstance()
        val viewPage=binding.viewPager
        val tableLayour=binding.tabLayout
        val db=FirebaseFirestore.getInstance().collection("Tiendas").document(firebaseAuth.uid.toString())
        db.get().addOnSuccessListener { res->
            if(res.exists()){
                val data=res.data
                val plan=data?.get("plan") as? String
                val adapter = adapterViewPager(supportFragmentManager)
                adapter.addFragmet(perfil_fr(), "Perfil")
                adapter.addFragmet(pedidos_fr(), "pedidos")
                if(plan=="intermedio" || plan=="premiun"){
                    adapter.addFragmet(noticias_fr(), "Noticias")
                    adapter.addFragmet(servicios_fr(), "servicios")
                    adapter.addFragmet(promociones_fr(), "Promociones")
                    adapter.addFragmet(articulos_fr(), "Articulos")
                }

                viewPage.adapter=adapter
                tableLayour.setupWithViewPager(viewPage)
            }
        }




    }



}