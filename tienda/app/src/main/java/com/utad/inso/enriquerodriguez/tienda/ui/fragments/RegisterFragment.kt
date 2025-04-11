package com.utad.inso.enriquerodriguez.tienda.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.utad.inso.enriquerodriguez.tienda.R
import com.utad.inso.enriquerodriguez.tienda.databinding.FragmentRegisterBinding
import com.utad.inso.enriquerodriguez.tienda.model.User

class RegisterFragment: Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://erdr-utad-inso-default-rtdb.europe-west1.firebasedatabase.app/")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnRegister.setOnClickListener {

            binding.btnRegister.setOnClickListener {
                auth.createUserWithEmailAndPassword(
                    binding.editCorreoRegister.text.toString(),
                    binding.editPassRegister.text.toString()
                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        Snackbar.make(binding.root, "Usuario Creado con exito", Snackbar.LENGTH_SHORT).show()

                        database.reference.child("users").child(auth.currentUser!!.uid).setValue(
                            User(nombre = binding.editNombreRegister.text.toString(),
                                correo = binding.editCorreoRegister.text.toString(),
                                telefono = binding.editPhoneRegister.text.toString().toInt()
                            )
                        )
                        val bundle = Bundle()
                        bundle.putString("correo", binding.editCorreoRegister.text.toString())
                        bundle.putString("pass", binding.editPassRegister.text.toString())
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment, bundle)
                    }
                    else{
                        Snackbar.make(binding.root, "Error en el proceso de registro", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}