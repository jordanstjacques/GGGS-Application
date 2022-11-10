package com.example.gggsapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gggsapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.moveToSignInEt.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.signUpBtn.setOnClickListener {
            val email = binding.enterEmailEt.text.toString()
            val password = binding.enterPasswordEt.text.toString()
            val confirmPassword = binding.confirmPassEt.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if(password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if(it.isSuccessful) {
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this,"Passwords are not matching", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Emtpy Fields Are Not Allowed!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}