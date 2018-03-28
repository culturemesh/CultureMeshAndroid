package org.codethechange.culturemesh;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {
    private boolean signInToggle = true;
    EditText firstNameText;
    EditText lastNameText;
    EditText confirmPassword;
    EditText passwordText;
    TextView needAccountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Button signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: Handle sign in.
                Intent returnIntent = new Intent();
                // TODO: Change result returned to RESULT_CANCELLED for no login
                // Set the current user in the SharedPreferences settings to the user that just logged in
                SharedPreferences settings = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                // TODO: Update the "1" here with the correct user ID
                editor.putLong(API.CURRENT_USER, 1);
                editor.apply();

                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        firstNameText = findViewById(R.id.first_name_field);
        lastNameText = findViewById(R.id.last_name_field);
        confirmPassword = findViewById(R.id.confirm_password_field);
        passwordText = findViewById(R.id.password_field);
        needAccountText = findViewById(R.id.need_account_text);
        final Button signToggleButton = findViewById(R.id.sign_toggle_button);
        //Get number of pixels for 8dp
        DisplayMetrics displaymetrics = new DisplayMetrics();
        final int eightDp = (int) getResources().getDimensionPixelSize(R.dimen.edit_text_spacing);
        signToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signInToggle) {
                    //Have animation move edit texts in place.
                    //Move first name text from bottom to just under username
                    Animation firstNameTextAnim = new Animation() {
                            @Override
                            protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                                    firstNameText.getLayoutParams();
                            params.topMargin = (int)(1000 - (1000 - eightDp) * interpolatedTime);
                            firstNameText.setLayoutParams(params);
                        }
                    };
                    firstNameTextAnim.setDuration(300); // in ms
                    firstNameText.startAnimation(firstNameTextAnim);
                    //Have Password move two EditText's below where it is now.
                    final Animation passwordTextAnim = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                                    passwordText.getLayoutParams();
                            params.topMargin = (int) (eightDp + interpolatedTime * (
                                    firstNameText.getMeasuredHeight() + eightDp +
                                            lastNameText.getMeasuredHeight()+ eightDp));
                            passwordText.setLayoutParams(params);
                        }
                    };
                    passwordTextAnim.setDuration(300);
                    passwordText.startAnimation(passwordTextAnim);
                    //Have confirmPasswordMove just below password.
                    final Animation confirmPasswordAnim = new Animation(){
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                                    confirmPassword.getLayoutParams();
                            params.topMargin = (int) (eightDp + interpolatedTime * (
                                    firstNameText.getMeasuredHeight() + eightDp +
                                            lastNameText.getMeasuredHeight() + eightDp +
                                            passwordText.getMeasuredHeight() + eightDp));
                            confirmPassword.setLayoutParams(params);
                        }
                    };
                    confirmPasswordAnim.setDuration(300);
                    confirmPassword.startAnimation(confirmPasswordAnim);
                    ConstraintSet constraints = new ConstraintSet();
                    ConstraintLayout layout = findViewById(R.id.login_layout);
                    constraints.clone(layout);
                    //Have sign up button be under confirm password field
                    constraints.connect(R.id.sign_in_button, ConstraintSet.TOP,
                            R.id.confirm_password_field, ConstraintSet.BOTTOM);
                    constraints.applyTo(layout);
                    signInToggle = false;
                    //Swap button labels.
                    signInButton.setText(getResources().getString(R.string.sign_up));
                    signToggleButton.setText(getResources().getString(R.string.sign_in));
                    //Update explanation text above toggle button.
                    needAccountText.setText(getResources().getString(R.string.already_have_account));
                } else {
                    //Change back to sign in layout.
                    //Have animation move edit texts in place.
                    //Move first name to bottom under username
                    Animation firstNameTextAnim = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                                    firstNameText.getLayoutParams();
                            params.topMargin = (int)(2000 * interpolatedTime);
                            firstNameText.setLayoutParams(params);
                        }
                    };
                    firstNameTextAnim.setDuration(300); // in ms
                    firstNameText.startAnimation(firstNameTextAnim);
                    //Have Password move back up.
                    final Animation passwordTextAnim = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                                    passwordText.getLayoutParams();
                            int totalHeight = firstNameText.getMeasuredHeight() + eightDp +
                                    lastNameText.getMeasuredHeight();
                            params.topMargin = (int) (totalHeight-  interpolatedTime *
                                    (totalHeight - eightDp));
                            passwordText.setLayoutParams(params);
                        }
                    };
                    passwordTextAnim.setDuration(300);
                    passwordText.startAnimation(passwordTextAnim);
                    //Have confirmPasswordMove just below password.
                    final Animation confirmPasswordAnim = new Animation(){
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                                    confirmPassword.getLayoutParams();
                            params.topMargin = (int) (2000 * interpolatedTime);
                            confirmPassword.setLayoutParams(params);
                        }
                    };
                    confirmPasswordAnim.setDuration(300);
                    confirmPassword.startAnimation(confirmPasswordAnim);
                    ConstraintSet constraints = new ConstraintSet();
                    ConstraintLayout layout = findViewById(R.id.login_layout);
                    constraints.clone(layout);
                    //Have sign in button be just under password field
                    constraints.connect(R.id.sign_in_button, ConstraintSet.TOP,
                            R.id.password_field, ConstraintSet.BOTTOM);
                    constraints.applyTo(layout);
                    signInToggle = true;
                    //Swap button labels.
                    signInButton.setText(getResources().getString(R.string.sign_in));
                    signToggleButton.setText(getResources().getString(R.string.sign_up));
                    //Update explanation text above toggle button.
                    needAccountText.setText(getResources().getString(R.string.need_account));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            if (getIntent().hasExtra(Redirection.LAUNCH_ON_FINISH_EXTRA)) {
                Class<?> nextActivity = (Class) getIntent().getSerializableExtra(Redirection.LAUNCH_ON_FINISH_EXTRA);
                Intent next = new Intent(getApplicationContext(), nextActivity);
                if (getIntent().hasExtra(Redirection.PASS_ON_FINISH_EXTRA)) {
                    next.putExtras(getIntent().getBundleExtra(Redirection.PASS_ON_FINISH_EXTRA));
                }
            }
        }

    }

}
