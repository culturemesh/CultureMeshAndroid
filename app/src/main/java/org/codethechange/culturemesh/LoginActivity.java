package org.codethechange.culturemesh;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.codethechange.culturemesh.models.User;


public class LoginActivity extends RedirectableAppCompatActivity {
    private boolean signInToggle = true;
    EditText firstNameText;
    EditText lastNameText;
    EditText confirmPassword;
    EditText passwordText;
    TextView needAccountText;

    /**
     * Largely for testing, this public method can be used to set which user is currently logged in
     * This is useful for PickOnboardingStatusActivity because different login states correspond
     * to different users. No logged-in user is signalled by a missing SharedPreferences entry.
     * @param settings The SharedPreferences storing user login state
     * @param userID ID of the user to make logged-in
     */
    public static void setLoggedIn(SharedPreferences settings, long userID) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(API.CURRENT_USER, userID);
        editor.apply();
    }

    public static boolean isLoggedIn(SharedPreferences settings) {
        return settings.contains(API.CURRENT_USER);
    }

    public static void setLoggedOut(SharedPreferences settings) {
        if (isLoggedIn(settings)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.remove(API.CURRENT_USER);
            editor.apply();
        }
    }

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
                EditText userNameField = findViewById(R.id.user_name_field);
                EditText passwordField = findViewById(R.id.password_field);
                String userName = userNameField.getText().toString();
                String password = passwordField.getText().toString();
                Credential cred = new Credential(userName, password);
                new ValidateCredentials().execute(cred);
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

    private class Credential {
        private String userName;
        private String password;

        public Credential(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
    }

    private class ValidateCredentials extends AsyncTask<Credential, Void, NetworkResponse<User>> {
        @Override
        protected NetworkResponse<User> doInBackground(Credential... credentials) {
            super.onPreExecute();

            API.loadAppDatabase(getApplicationContext());
            NetworkResponse<User> res;
            try {
                // TODO: Replace this with actually processing username and password
                //TODO: Handle sign in.
                long id = Long.parseLong(credentials[0].userName);
                res = API.Get.user(id);
            } catch (NumberFormatException e) {
                res = new NetworkResponse<>(true, R.string.loginFailed);
            }

            API.closeDatabase();
            return res;
        }

        @Override
        protected void onPostExecute(NetworkResponse<User> userNetworkResponse) {
            super.onPostExecute(userNetworkResponse);
            if (userNetworkResponse.fail()) {
                userNetworkResponse.showErrorDialog(LoginActivity.this);
            } else {
                SharedPreferences settings = getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
                setLoggedIn(settings, userNetworkResponse.getPayload().id);

                Intent returnIntent = new Intent();
                // TODO: Change result returned to RESULT_CANCELLED for no login
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }
    }

}