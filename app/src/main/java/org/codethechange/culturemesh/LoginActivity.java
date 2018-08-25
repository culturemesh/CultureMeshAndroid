package org.codethechange.culturemesh;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.codethechange.culturemesh.models.User;

/**
 * Login screen that lets a user either sign in with email and password or create a new account
 */
public class LoginActivity extends RedirectableAppCompatActivity {

    /**
     * Whether the user is signing in or creating an account ({@code true} if signing in)
     */
    private boolean signInToggle = true;

    /**
     * Reference to the text field for the user's first name
     */
    EditText firstNameText;

    /**
     * Reference to the text field for the user's last name
     */
    EditText lastNameText;

    /**
     * Reference to the text field for password confirmation
     */
    EditText confirmPassword;

    /**
     * Reference to the text field for the user's password
     */
    EditText passwordText;

    /**
     * Reference to the text field for the user's username
     */
    EditText usernameText;

    /**
     * Text field the user can click to toggle between creating an account and signing in
     */
    TextView needAccountText;

    /**
     * Queue for asynchronous tasks
     */
    private RequestQueue queue;

    /**
     * Largely for testing, this public method can be used to set which user is currently logged in
     * This is useful for PickOnboardingStatusActivity because different login states correspond
     * to different users. No logged-in user is signalled by a missing SharedPreferences entry.
     * @param settings The SharedPreferences storing user login state
     * @param userID ID of the user to make logged-in
     */
    public static void setLoggedIn(SharedPreferences settings, long userID, String email) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(API.CURRENT_USER, userID);
        editor.putString(API.USER_EMAIL, email);
        editor.apply();
    }

    /**
     * Check whether any user is currently signed in
     * @param settings The app's shared settings, which store user preferences
     * @return {@code true} if a user is signed in, {@code false} otherwise
     */
    public static boolean isLoggedIn(SharedPreferences settings) {
        return settings.contains(API.CURRENT_USER);
    }

    /**
     * Logout the currently logged-out user. If no user is logged in, nothing happens
     * @param settings The app's shared settings, which store user preferences
     */
    public static void setLoggedOut(SharedPreferences settings) {
        if (isLoggedIn(settings)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.remove(API.CURRENT_USER);
            editor.apply();
        }
    }

    /**
     * Helper method that logs the user in using the provided credentials
     * @param queue Queue to which the asynchronous task will be added
     * @param email User's account email address
     * @param password User's password
     */
    private void login(RequestQueue queue, final String email, String password) {
        API.Get.loginWithCred(queue, email, password,
                getSharedPreferences(API.SETTINGS_IDENTIFIER, MODE_PRIVATE),
                new Response.Listener<NetworkResponse<API.Get.LoginResponse>>() {
            @Override
            public void onResponse(NetworkResponse<API.Get.LoginResponse> response) {
                if (response.fail()) {
                    NetworkResponse.genErrorDialog(LoginActivity.this, R.string.authenticationError).show();
                } else {
                    SharedPreferences settings = getSharedPreferences(
                            API.SETTINGS_IDENTIFIER, MODE_PRIVATE);
                    API.Get.LoginResponse bundle = response.getPayload();
                    User user = bundle.user;
                    setLoggedIn(settings, user.id, email);
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }

    /**
     * Create the user interface from {@link R.layout#activity_login}. Also setup buttons to perform
     * the associated actions, including log-ins with
     * {@link API.Get#loginWithCred(RequestQueue, String, String, SharedPreferences,
     * Response.Listener)}
     * and account creation with
     * {@link API.Post#user(RequestQueue, User, String, String, Response.Listener)}. Also sets up
     * the animations to convert between signing in and creating an account.
     * @param savedInstanceState {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Button signInButton = findViewById(R.id.sign_in_button);
        queue = Volley.newRequestQueue(getApplicationContext());
        firstNameText = findViewById(R.id.first_name_field);
        lastNameText = findViewById(R.id.last_name_field);
        confirmPassword = findViewById(R.id.confirm_password_field);
        passwordText = findViewById(R.id.password_field);
        needAccountText = findViewById(R.id.need_account_text);
        usernameText = findViewById(R.id.username_field);
        final Button signToggleButton = findViewById(R.id.sign_toggle_button);
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (signInToggle) {
                    EditText emailField = findViewById(R.id.email_field);
                    EditText passwordField = findViewById(R.id.password_field);
                    final String email = emailField.getText().toString();
                    final String password = passwordField.getText().toString();
                    login(queue, email, password);
                } else {
                    EditText emailField = findViewById(R.id.email_field);
                    EditText firstNameField = findViewById(R.id.first_name_field);
                    EditText lastNameField = findViewById(R.id.last_name_field);
                    EditText passwordField = findViewById(R.id.password_field);
                    EditText confirmPasswordField = findViewById(R.id.confirm_password_field);

                    final String pass = passwordField.getText().toString();
                    final String confPass = confirmPasswordField.getText().toString();
                    final String email = emailField.getText().toString();
                    if (!pass.equals(confPass)) {
                        (new NetworkResponse<Void>(true,
                                R.string.passwords_dont_match)).showErrorDialog(LoginActivity.this);
                    } else {
                        String username = usernameText.getText().toString();
                        User userToCreate = new User(-1, firstNameField.getText().toString(),
                                lastNameField.getText().toString(),
                                username);
                        API.Post.user(queue, userToCreate, email, pass, new Response.Listener<NetworkResponse<String>>() {
                            @Override
                            public void onResponse(NetworkResponse<String> response) {
                                if (response.fail()) {
                                    response.showErrorDialog(LoginActivity.this);
                                } else {
                                    login(queue, email, pass);
                                }
                            }
                        });
                    }
                }
            }
        });
        //Get number of pixels for 8dp
        DisplayMetrics displaymetrics = new DisplayMetrics();
        final int eightDp = (int) getResources().getDimensionPixelSize(R.dimen.edit_text_spacing);
        signToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signInToggle) {
                    //Have animation move edit texts in place.
                    //Move user name text from bottom to just under email
                    Animation userNameTextAnim = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                                    usernameText.getLayoutParams();
                            params.topMargin = (int)(1000 - (1000 - eightDp) * interpolatedTime);
                            usernameText.setLayoutParams(params);
                        }
                    };
                    userNameTextAnim.setDuration(300); // in ms
                    usernameText.startAnimation(userNameTextAnim);
                    //Next, move first name to under user name.
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
                    //Have Password move three EditText's below where it is now.
                    final Animation passwordTextAnim = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                                    passwordText.getLayoutParams();
                            params.topMargin = (int) (eightDp + interpolatedTime * (
                                    firstNameText.getMeasuredHeight() + eightDp +
                                    usernameText.getMeasuredHeight() + eightDp +
                                            lastNameText.getMeasuredHeight()+ eightDp));
                            passwordText.setLayoutParams(params);
                        }
                    };
                    passwordTextAnim.setDuration(300);
                    passwordText.startAnimation(passwordTextAnim);
                    //Have confirmPassword move just below password.
                    final Animation confirmPasswordAnim = new Animation(){
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                                    confirmPassword.getLayoutParams();
                            params.topMargin = (int) (eightDp + interpolatedTime * (
                                    firstNameText.getMeasuredHeight() + eightDp +
                                            usernameText.getMeasuredHeight() + eightDp +
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
                    // We need to have move firstname to be under username as opposed to under
                    // email.
                    constraints.connect(R.id.first_name_field, ConstraintSet.TOP,
                            R.id.username_field, ConstraintSet.BOTTOM);
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
                    //Move first name to bottom under email
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
                    Animation usernameAnim = new Animation() {
                        @Override
                        protected void applyTransformation(float interpolatedTime, Transformation t) {
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                                    usernameText.getLayoutParams();
                            params.topMargin = (int)(2000 * interpolatedTime);
                            usernameText.setLayoutParams(params);
                        }
                    };
                    usernameAnim.setDuration(300);
                    usernameText.startAnimation(usernameAnim);
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

}