package org.codethechange.culturemesh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.codethechange.culturemesh.models.User;

public class ChangePasswordActivity extends AppCompatActivity {

    private int PASSWORD_CHANGE_SUCCESS = 2;
    private int PASSWORD_CHANGE_FAILURE = 1;
    private int NETWORK_FAILURE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        API.loadAppDatabase(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        API.closeDatabase();
    }

    public void submitChanges(View view) {
        EditText op = findViewById(R.id.old_password_edit);
        EditText np = findViewById(R.id.new_password_edit);
        EditText cp = findViewById(R.id.confirm_password_edit);

        String oldPassword = op.getText().toString();
        String newPassword = np.getText().toString();
        String confirmPassword = cp.getText().toString();

        API.loadAppDatabase(getApplicationContext());

        if (newPassword.equals(confirmPassword)) {
            if(!newPassword.isEmpty() && !oldPassword.isEmpty() && !confirmPassword.isEmpty()) {
                long userID = getIntent().getLongExtra("userID", 0);
                //TODO: uncomment when API is updated to reflect password changes
                // int response = API.Put.changePassword(userID, oldPassword, newPassword).getPayload();
                int response = PASSWORD_CHANGE_FAILURE;
                if(response == PASSWORD_CHANGE_SUCCESS) {
                    Toast.makeText(this, "Password updated!", Toast.LENGTH_SHORT).show();
                } else {
                    if(response == PASSWORD_CHANGE_FAILURE){
                        Toast.makeText(this, "Old Password Incorrect", Toast.LENGTH_SHORT).show();
                    } else if (response == NETWORK_FAILURE) {
                        Toast.makeText(this, "Network Failure", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Fill out all fields!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Make sure your new passwords match!", Toast.LENGTH_SHORT).show();
        }
        API.closeDatabase();
    }
}
