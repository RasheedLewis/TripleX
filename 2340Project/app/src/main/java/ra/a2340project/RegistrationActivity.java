package ra.a2340project;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;

/**
 * Screen that allows the current user to register
 *
 * Created by Ben Hepburn on 2/21/17.
 */

/**
 * A registration screen that allows a user to create an account
 */
public class RegistrationActivity extends AppCompatActivity{
    private static final String TAG = "RegistrationActivity";


    private FirebaseAuth mAuth;

    private EditText _accountNameText;
    private EditText _accountEmailText;
    private EditText _accountUsernameText;
    private EditText _accountPasswordText;
    private EditText _accountConfirmPassText;
    private Spinner _statusSpinner;
    private Button _registerButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        _accountNameText = (EditText) findViewById(R.id.name);
        _accountEmailText = (EditText) findViewById(R.id.email);
        _accountUsernameText = (EditText) findViewById(R.id.username);
        _accountPasswordText = (EditText) findViewById(R.id.password);
        _accountConfirmPassText = (EditText) findViewById(R.id.confirm_password);
        _statusSpinner = (Spinner) findViewById(R.id.status_spinner);
        _registerButton = (Button) findViewById(R.id.button_register);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, User.statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _statusSpinner.setAdapter(adapter);

        _registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                register();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Saves all entered data when registration button is entered
     * Adds new user to user hash map
     */
    private void register() {
        Log.d(TAG, "Register");
        Model _model = Model.getInstance();
        if (!validate()) {
            onRegisterFailed();
            return;
        }

        User _user;
        String username = _accountUsernameText.getText().toString();
        _user = new User(username);
        _user.setName(_accountNameText.getText().toString());
        _user.setEmail(_accountEmailText.getText().toString());
        _user.setPassword(_accountPasswordText.getText().toString());
        _user.setUsername(_accountUsernameText.getText().toString());
        _user.setStatus((String) _statusSpinner.getSelectedItem());

        _model.addUser(_user.getUsername(),_user);
        _model.setCurrentUser(_user);

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(_accountUsernameText.getText().toString() + "@waterworks.com",
                _accountPasswordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Toast.makeText(RegistrationActivity.this, R.string.error_field_required,
//                                    Toast.LENGTH_SHORT).show();
//                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]

        _registerButton.setEnabled(false);
    }

    private void onRegisterFailed() {
        _registerButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * Checks all input information
     *
     * @return boolean indicating if all information entered is valid
     */
    private boolean validate() {
        Model model = Model.getInstance();

        boolean valid = true;

        String name = _accountNameText.getText().toString();
        String email = _accountEmailText.getText().toString();
        String username = _accountUsernameText.getText().toString();
        String password = _accountPasswordText.getText().toString();
        String confirmPass = _accountConfirmPassText.getText().toString();

        if (name.isEmpty()) {
            _accountNameText.setError("Enter a valid name");
            valid = false;
        }

        if (email.isEmpty()) {
            _accountEmailText.setError("Enter a valid email");
            valid = false;
        }

        if (username.isEmpty()) {
            _accountUsernameText.setError("Enter a valid username");
            valid = false;
        }

        if (model.getUserHashMap().containsKey(username)) {
            _accountUsernameText.setError("Username is already taken");
            valid = false;
        }

        if (password.isEmpty()) {
            _accountPasswordText.setError("Enter a valid password");
            valid = false;
        }

        if (confirmPass.isEmpty()) {
            _accountConfirmPassText.setError("You did not confirm your password");
            valid = false;
        }

        if (!password.equals(confirmPass)) {
            _accountConfirmPassText.setError("Your passwords do not match");
            valid = false;
        }

        if(model.getUserHashMap().containsKey(username)) {
            _accountUsernameText.setError("This username already exists. Pick another.");
            valid = false;
        }

        return valid;
    }
}
