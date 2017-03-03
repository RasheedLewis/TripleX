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

    @Bind(R.id.profile_edit_name) EditText _profileNameText;
    @Bind(R.id.profile_edit_email) EditText _profileEmailText;
    @Bind(R.id.profile_edit_username) EditText _profileUsernameText;
    @Bind(R.id.status_spinner_profile_edit) Spinner _profileStatusSpinner;
    @Bind(R.id.button_profile_edit_Done) Button _profileDoneButton;

    private User _user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        ButterKnife.bind(this);

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

    public void edit() {
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

    public boolean validate () {
        boolean valid = true;
        Model model = Model.getInstance();
        _user = model.getCurrentUser();

        String username = _profileUsernameText.getText().toString();

        if (model.getUserHashMap().containsKey(username) && !(username.equals(model.getUserHashMap().get(_user.getUsername())))) {
            _profileUsernameText.setError("Username is already taken");
            valid = false;
        } else {
            _profileUsernameText.setError(null);
        }

        return valid;
    }

    public void onEditFailed() {
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
