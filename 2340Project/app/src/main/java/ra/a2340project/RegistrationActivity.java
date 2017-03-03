package ra.a2340project;

import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.content.Intent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ben Hepburn on 2/21/17.
 */

/**
 * A registration screen that allows a user to create an account
 */
public class RegistrationActivity extends AppCompatActivity{
    private static final String TAG = "RegistrationActivity";

    @Bind(R.id.name) EditText _accountNameText;
    @Bind(R.id.email) EditText _accountEmailText;
    @Bind(R.id.username) EditText _accountUsernameText;
    @Bind(R.id.password) EditText _accountPasswordText;
    @Bind(R.id.confirm_password) EditText _accountConfirmPassText;
    @Bind(R.id.status_spinner) Spinner _statusSpinner;
    @Bind(R.id.button_register) Button _registerButton;


    private User _user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, User.statuses);
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
    }

    /**
     * Saves all entered data when registration button is entered
     * Adds new user to user hash map
     */
    public void register() {
        Log.d(TAG, "Register");
        Model model = Model.getInstance();
        if (!validate()) {
            onRegisterFailed();
            return;
        }

        String username = _accountUsernameText.getText().toString();
        _user = new User(username);
        _user.setName(_accountNameText.getText().toString());
        _user.setEmail(_accountEmailText.getText().toString());
        _user.setPassword(_accountPasswordText.getText().toString());
        _user.setUsername(_accountUsernameText.getText().toString());
        _user.setStatus((String) _statusSpinner.getSelectedItem());

        model.addUser(_user.getUsername(),_user);
        model.setCurrentUser(_user);

        _registerButton.setEnabled(false);
    }

    public void onRegisterFailed() {
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
     * Checks all input informaiton
     *
     * @return boolean indicating if all information entered is valid
     */
    public boolean validate() {
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

        if (password.equals(confirmPass) == false) {
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
