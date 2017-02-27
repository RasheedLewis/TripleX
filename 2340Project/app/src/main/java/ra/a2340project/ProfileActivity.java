package ra.a2340project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.ButterKnife;

/**
 * Created by benhepburn on 2/22/17.
 */

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        Model model = Model.getInstance();
        user = model.getCurrentUser();

        TextView nameTextView = (TextView) findViewById(R.id.profile_name);
        nameTextView.setText("Name: " + user.getName());

        TextView emailTextView = (TextView) findViewById(R.id.profile_email);
        emailTextView.setText("Email: " + user.getEmail());

        TextView usernameTextView = (TextView) findViewById((R.id.profile_username));
        usernameTextView.setText("Username: " + user.getUsername());

        TextView statusTextView = (TextView) findViewById(R.id.profile_status);
        statusTextView.setText("Status: " + user.getStatus());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
