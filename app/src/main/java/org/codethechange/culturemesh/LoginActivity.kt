package org.codethechange.culturemesh

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var signInToggle = true
    internal var firstNameText: EditText? = null
    internal var lastNameText: EditText? = null
    internal var confirmPassword: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar);
        val signInButton = findViewById<Button>(R.id.sign_in_button) as Button
        signInButton.setOnClickListener {
            //TODO: Handle sign in
            finish()
        }
        val signToggleButton = findViewById<Button>(R.id.sign_toggle_button) as Button
        signToggleButton.setOnClickListener {
            if (signInToggle) {
                //Have animation move edit texts in place.
                firstNameText = findViewById(R.id.first_name_field)
                lastNameText = findViewById(R.id.last_name_field)
                confirmP


            }
        }
    }

}
