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
import com.utad.inso.enriquerodriguez.tienda.R
import com.utad.inso.enriquerodriguez.tienda.databinding.FragmentLoginBinding

class LoginFragment: Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding
    private var correo: String? = null
    private var pass: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        auth = FirebaseAuth.getInstance()

        correo = arguments?.getString("correo")
        pass = arguments?.getString("pass")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.editCorreo.setText(correo?:"")
        binding.editPass.setText(pass?:"")

        binding.btnCrear.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnInicio.setOnClickListener {
            auth.signInWithEmailAndPassword(
                binding.editCorreo.text.toString(),
                binding.editPass.text.toString()
            ).addOnCompleteListener{
                if(it.isSuccessful){
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }else{
                    Snackbar.make(binding.root, "Fallo al iniciar sesion", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

}