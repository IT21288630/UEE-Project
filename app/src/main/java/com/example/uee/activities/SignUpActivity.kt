package com.example.uee.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.uee.R
import com.google.android.material.textfield.TextInputLayout


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Val initiations -------------------------------------------------------------------------
        val signUpBtn = findViewById<Button>(R.id.BtnSignUp)
        val goToLoginText = findViewById<TextView>(R.id.goToLoginTxt)
        val getEmailTxt = findViewById<TextInputLayout>(R.id.SignupEmailField)


        val items = listOf("Client", "Caregiver")
        val adapter = ArrayAdapter(this, R.layout.signup_item, items)
        val getType = findViewById<AutoCompleteTextView>(R.id.textField)
        getType.setAdapter(adapter)



        // Click listner actions ------------------------------------------------------------------
        signUpBtn.setOnClickListener(){

            val emailTemp = getEmailTxt?.editText?.text.toString()
            val accountType = getType?.text.toString()

            if (isEmailValid(emailTemp)) {
                // Email is valid
                if(accountType == "Client"){
                    val intentSignUp = Intent(this,ClientRegistrationActivity::class.java)
                    intentSignUp.putExtra("email", emailTemp)
                    intentSignUp.putExtra("accountType", accountType)
                    startActivity(intentSignUp)
                }else if(accountType == "Caregiver"){

                    val intentSignUp = Intent(this,CaregiverRegisterActivity::class.java)
                    startActivity(intentSignUp)
                }

            } else {
                // Email is not in the valid format
                getEmailTxt?.error = "Please enter a valid email"
            }


        }

        goToLoginText.setOnClickListener(){
            val intentGoToLogin = Intent(this,LoginActivity::class.java)
            startActivity(intentGoToLogin)
        }

    }

    fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
        return email.matches(emailRegex)
    }
}