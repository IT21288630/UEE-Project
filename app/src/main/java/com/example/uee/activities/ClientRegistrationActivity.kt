package com.example.uee.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.uee.R
import com.example.uee.dataClasses.Client
import com.example.uee.fragments.ClientRegistration1Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ClientRegistrationActivity : AppCompatActivity() {

    private var client = Client()
    private val clientCollectionRef = Firebase.firestore.collection("Clients")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_registration)


        val receivedEmail = intent.getStringExtra("email")
        val receivedAccountType = intent.getStringExtra("accountType")

        client.email = receivedEmail
        client.userType = receivedAccountType

        setCurrentFragment(ClientRegistration1Fragment())

    }

    fun updateClientDataReg1(userName: String?, password: String?) {
        client.userName = userName
        client.psw = password
    }

    fun updateClientDataReg2(Name: String?, age:String?, location:String?,Contact:String?,about:String?, imageURL : String?) {
        client.name = Name
        client.age = age
        client.location = location
        client.contactNo = Contact
        client.about = about
        client.proPic = imageURL

        Log.d("ClientRegistration1Fragment", "updateClientDataReg2 runs")

        if(client != null){

            saveClientToFirestore()

            val clientData = client

            val sharedPreferences = getSharedPreferences("appPref", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            //Creating shared pref for new user.
            editor.putString("userName", client.userName)
            editor.putString("userType", client.userType)
            editor.apply()

            val loadClient = Intent(this@ClientRegistrationActivity, ClientUIActivity::class.java)
            loadClient.putExtra("clientData", clientData)
            startActivity(loadClient)



        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.RegFragContainer, fragment)
            commit()
        }
    }

    private fun saveClientToFirestore() : Boolean {

        val status = clientCollectionRef.add(client).isSuccessful

        Log.d("ClientRegistration1Fragment", "saveClientToFirestore status :  ${status}")
        return status
    }
}