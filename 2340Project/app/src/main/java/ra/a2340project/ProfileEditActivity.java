package ra.a2340project;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.os.Bundle;
import android.content.Intent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by benhepburn on 2/27/17.
 */

public class ProfileEditActivity extends AppCompatActivity {
    private static final String TAG = "ProfileEditActivity";

    private EditText _profileNameText;
    private EditText _profileEmailText;
    private EditText _profileUsernameText;
    private Spinner _profileStatusSpinner;
    private Button _profileDoneButton;

    private User _user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        ButterKnife.bind(this);

        _profileDoneButton = (Button) findViewById(R.id.button_profile_edit_Done);
        _profileStatusSpinner = (Spinner) findViewById(R.id.status_spinner_profile_edit);
        _profileUsernameText = (EditText) findViewById(R.id.profile_edit_username);
        _profileEmailText = (EditText) findViewById(R.id.profile_edit_email);
        _profileNameText = (EditText) findViewById(R.id.profile_edit_name);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, User.statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _profileStatusSpinner.setAdapter(adapter);

        _profileDoneButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                edit();

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private void edit() {
        Log.d(TAG, "Edit");
        Model model = Model.getInstance();

        if (!validate()) {
            onEditFailed();
            return;
        }

        _user = model.getCurrentUser();
        _user.setName(_profileNameText.getText().toString());
        _user.setEmail(_profileEmailText.getText().toString());
        _user.setPassword(model.getUserHashMap().get(model.getCurrentUser().getUsername()).getPassword());
        _user.setUsername(_profileUsernameText.getText().toString());
        _user.setStatus((String) _profileStatusSpinner.getSelectedItem());

        model.addUser(_user.getUsername(), _user);

        _profileDoneButton.setEnabled(false);
    }

    private boolean validate () {
        boolean valid = true;
        Model model = Model.getInstance();
        _user = model.getCurrentUser();

        String username = _profileUsernameText.getText().toString();

        if (model.getUserHashMap().containsKey(username) && !(username.equals(model.getUserHashMap().get(_user.getUsername()).getUsername()))) {
            _profileUsernameText.setError("Username is already taken");
            valid = false;
        } else {
            _profileUsernameText.setError(null);
        }

        return valid;
    }

    private void onEditFailed() {
        _profileDoneButton.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
