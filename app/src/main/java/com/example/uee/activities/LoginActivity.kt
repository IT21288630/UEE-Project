package com.example.uee.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uee.R
import com.example.uee.dataClasses.Caregiver
import com.example.uee.dataClasses.Client
import com.example.uee.dataClasses.LoginUser
import com.google.android.material.textfield.TextInputLayout
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
    private val clientCollectionRef = Firebase.firestore.collection("Clients")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPref = getSharedPreferences("appPref", MODE_PRIVATE)
        val editor = sharedPref.edit()
        val userType = sharedPref.getString("userType", null)

        val etUName: TextInputLayout = findViewById(R.id.loginUserNameField)
        val etPassword: TextInputLayout = findViewById(R.id.loginPswField)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            /*@ToDo - search both Caregiver collection and Client collection. Get the type of matched collection. If matched with caregiver, load the caregiver UI, if matched with the client load client UI. Save user type in sharePref */

            /* User selection Logic (USL) -
                step 1- First check the client collection for the name, then get the user object if available and proceed with the Login function.
            *   step 2 - If the name is not available in clients, check the caregiver collection and get the user object
                step 3 - If the username doesn't exist in both collection, display a pop up to sign up.

            *  Login logic (LL) -
                   step 1 - check the userObject psw with given psw, if valid proceed to step 2, if not display"password mismatch"
                   step 2 - create a sharedPref called "appPref" to store userType and userName
                   step 2.1 - Check the accType and then proceed to relevant UI
             */

            //Removing any accidental data saved in sharedPref
            editor.clear()
            editor.commit()

            //USL-step 1
            val user = LoginUser(etUName.editText?.text.toString(), etPassword.editText?.text.toString())
            searchClientCollection(user)


        }


    }

    //USL - step 1 - select from Client collection
    private fun searchClientCollection(loginUser: LoginUser) = CoroutineScope(Dispatchers.Main).launch {

        try {
            val querySnapshot = clientCollectionRef.get().await()

            for (document in querySnapshot){
                var temp = document.toObject<Client>()

                if (temp.userName == loginUser.userName){

                    if (temp.psw == loginUser.psw){
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@LoginActivity, "Hello ${temp.name}", Toast.LENGTH_LONG).show()

                            loginUser.userType = "client"
                            // call login function
                            loginTheUser(loginUser)
                        }
                    }else{
                        withContext(Dispatchers.Main){
                            //Display password mismatch pop up window
                            Toast.makeText(this@LoginActivity, "wrong password for client", Toast.LENGTH_LONG).show()

                        }
                    }
                }else{
                    withContext(Dispatchers.Main){
                        searchCaregiverCollection(loginUser)
                    }
                }
            }
        }
        catch (e: Exception){
            Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
        }

    }

    //Search from caregivers
    private fun searchCaregiverCollection(loginUser: LoginUser) = CoroutineScope(Dispatchers.Main).launch {

        try {
            val querySnapshot = caregiverCollectionRef.get().await()

            for (document in querySnapshot){
                var temp = document.toObject<Caregiver>()

                if (temp.username == loginUser.userName){

                    if (temp.password == loginUser.psw){
                        withContext(Dispatchers.Main){
                            //Call login function
                            Toast.makeText(this@LoginActivity, "Hello ${temp.name}", Toast.LENGTH_LONG).show()
                            loginUser.userType = "caregiver"

                            loginTheUser(loginUser)

                        }
                    }else{
                        withContext(Dispatchers.Main){
                            //Display psw mismatch pop up
                            Toast.makeText(this@LoginActivity, "wrong password for caregiver account", Toast.LENGTH_LONG).show()
                        }
                    }
                }else{
                    withContext(Dispatchers.Main){
                        //Display no such account is with us - sign in
                        Toast.makeText(this@LoginActivity, "No such user name exist", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        catch (e: Exception){
            Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
        }

    }

    private suspend fun loginTheUser(validUser : LoginUser) {

        val sharedPreferences = getSharedPreferences("appPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("userName", validUser.userName)
        editor.putString("userType", validUser.userType)
        editor.apply() // Or editor.commit() for immediate save



        if (validUser.userType == "client") {
            CoroutineScope(Dispatchers.Main).launch {
                val user = getClientObject(validUser)
                val intent = Intent(this@LoginActivity, ClientUIActivity::class.java)
                intent.putExtra("clientData", user)
                startActivity(intent)
            }
        }else if(validUser.userType == "caregiver"){

            CoroutineScope(Dispatchers.Main).launch {
                val user = getCaregiverObject(validUser)
                val intent = Intent(this@LoginActivity, CaregiverActivity::class.java)
                intent.putExtra("caregiverData", user)
                startActivity(intent)
            }
        }


    }


    private suspend fun getClientObject(getUser: LoginUser): Client? = withContext(Dispatchers.IO) {
        var clientObj: Client? = null
        try {
            val querySnapshot = clientCollectionRef.get().await()
            for (document in querySnapshot) {
                val temp = document.toObject<Client>()
                if (temp.userName == getUser.userName) {
                    clientObj = temp
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
        clientObj
    }

    private suspend fun getCaregiverObject(getUser: LoginUser): Caregiver? = withContext(Dispatchers.IO) {
        var caregiverObj: Caregiver? = null
        try {
            val querySnapshot = caregiverCollectionRef.get().await()
            for (document in querySnapshot) {
                val temp = document.toObject<Caregiver>()
                if (temp.username == getUser.userName) {
                    caregiverObj = temp
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
       caregiverObj
    }




}