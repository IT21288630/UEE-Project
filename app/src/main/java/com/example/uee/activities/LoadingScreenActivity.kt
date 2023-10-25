package com.example.uee.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.uee.R
import com.example.uee.dataClasses.Caregiver
import com.example.uee.dataClasses.Client
import com.example.uee.dataClasses.LoginUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoadingScreenActivity : AppCompatActivity() {

    private val caregiverCollectionRef = Firebase.firestore.collection("Caregivers")
    private val clientCollectionRef = Firebase.firestore.collection("Clients")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        CoroutineScope(Dispatchers.Main).launch {
            //Checking shared preferences to configure the UI
            val sharedPreferences = getSharedPreferences("appPref", Context.MODE_PRIVATE)

            if (!sharedPreferences.contains("userName") || !sharedPreferences.contains("userType")) {
                //Loading first Loading page since preferences do not exist or are incomplete
                val loadFirstLoading = Intent(this@LoadingScreenActivity, FirstLoadingActivity::class.java)
                startActivity(loadFirstLoading)

            } else {
                val accType = sharedPreferences.getString("userType", "")

                when (accType) {
                    "client" -> {
                        // Action for "Client"
                        val clientName = sharedPreferences.getString("userName", "")
                        val userData = getUserObject(clientName, accType)
                        val clientData = userData as Client

                        val loadClient = Intent(this@LoadingScreenActivity, ClientUIActivity::class.java)
                        loadClient.putExtra("clientData", clientData)
                        startActivity(loadClient)


                    }

                    "caregiver" -> {
                        // Action for "Caregiver"
                        val caregiverName = sharedPreferences.getString("userName", "")
                        val userData = getUserObject(caregiverName, accType)
                        val caregiverData = userData as Caregiver

                        val loadCareGiver = Intent(this@LoadingScreenActivity, CaregiverActivity::class.java)
                        loadCareGiver.putExtra("caregiverData", caregiverData)
                        startActivity(loadCareGiver)


                    }

                    else -> {
                        Toast.makeText(
                            this@LoadingScreenActivity,
                            "Something went wrong. Please Login again",
                            Toast.LENGTH_LONG
                        ).show()

                        //Forcing app to wait 1.5 seconds before redirecting to Login page.
                        val handler = Handler(Looper.getMainLooper())
                        handler.postDelayed({

                            val loadLogin = Intent(this@LoadingScreenActivity, LoginActivity::class.java)
                            startActivity(loadLogin)
                        }, 1500) // 1500 milliseconds = 3 seconds
                    }
                }

            }
        }
    }

    private suspend fun getUserObject(name: String?, type: String?): Any? = withContext(Dispatchers.Main) {
        val user = LoginUser(userName = name, userType = type, psw = null)
        var clientObject: Client? = null
        var caregiverObject: Caregiver? = null

        if (type == "client") {
            clientObject = getClientObject(user)
        } else if (type == "caregiver") {
            caregiverObject = getCaregiverObject(user)
        }

        clientObject ?: caregiverObject
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
                Toast.makeText(this@LoadingScreenActivity, e.message, Toast.LENGTH_LONG).show()
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
                Toast.makeText(this@LoadingScreenActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
        caregiverObj
    }

}