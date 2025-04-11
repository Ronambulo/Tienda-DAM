package com.utad.inso.enriquerodriguez.tienda.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.utad.inso.enriquerodriguez.tienda.R
import com.utad.inso.enriquerodriguez.tienda.databinding.FragmentLoginBinding
import com.utad.inso.enriquerodriguez.tienda.databinding.FragmentMainBinding
import java.util.zip.Inflater

class MainFragment: Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var binding: FragmentMainBinding
    private var correo: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://erdr-utad-inso-default-rtdb.europe-west1.firebasedatabase.app/")
        correo = arguments?.getString("correo")
        Log.v("datos", auth.currentUser!!.uid)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.textoMain.text = correo

        binding.btnConsulta.setOnClickListener {
            database.reference.child("aplicacion").child("caracteristicas")
                .child("nombre").get().addOnSuccessListener {
                binding.textoMain.text =  it.value.toString()
            }

//            database.reference.child("aplicacion").child("caracteristicas")
//                .child("nombre")
        }

        binding.btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

        binding.btnEscribir.setOnClickListener {
            database.reference.child("aplicacion").child("fecha").setValue(null)
        }
    }

}