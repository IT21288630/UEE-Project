package com.example.uee.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uee.R
import com.example.uee.dataClasses.Caregiver
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val caregiverCollectionRef = Firebase.firestore.collection("Caregivers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPref = getSharedPreferences("appPref", MODE_PRIVATE)
        val userType = sharedPref.getString("userType", null)

        val etUName: EditText = findViewById(R.id.loginUserNameField)
        val etPassword: EditText = findViewById(R.id.loginPswField)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            if (userType.equals("caregiver")){
                val caregiver = Caregiver(etUName.text.toString(), etPassword.text.toString())

                cgLogin(caregiver)
            }
        }


    }

    private fun cgLogin(caregiver: Caregiver) = CoroutineScope(Dispatchers.Main).launch {

        try {
            val querySnapshot = caregiverCollectionRef.get().await()

            for (document in querySnapshot){
                var temp = document.toObject<Caregiver>()

                if (temp.username == caregiver.username){

                    if (temp.password == caregiver.password){
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@LoginActivity, "Hello ${temp.name}", Toast.LENGTH_LONG).show()
                        }
                    }else{
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@LoginActivity, "wrong password", Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@LoginActivity, "wrong username", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        catch (e: Exception){
            Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
        }

    }

}