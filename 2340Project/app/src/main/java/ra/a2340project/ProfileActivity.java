package ra.a2340project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by benhepburn on 2/22/17.
 */

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    @Bind(R.id.button_profile_Edit) Button _profileEditButton;

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        Model model = Model.getInstance();
        user = model.getCurrentUser();

        TextView nameTextView = (TextView) findViewById(R.id.profile_name);
        nameTextView.setText(user.getName());

        TextView emailTextView = (TextView) findViewById(R.id.profile_email);
        emailTextView.setText(user.getEmail());

        TextView usernameTextView = (TextView) findViewById((R.id.profile_username));
        usernameTextView.setText(user.getUsername());

        TextView statusTextView = (TextView) findViewById(R.id.profile_status);
        statusTextView.setText(user.getStatus());

        _profileEditButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileEditActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
