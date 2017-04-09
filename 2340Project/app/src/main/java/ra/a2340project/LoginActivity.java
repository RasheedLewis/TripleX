package ra.a2340project;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGN_UP = 0;

    private Button _loginButton;

    private boolean result;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        _loginButton = (Button) findViewById(R.id.btn_login);
        TextView _sign_upLink = (TextView) findViewById(R.id.link_sign_up);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (login()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    _loginButton.setEnabled(true);
                }
            }
        });

        _sign_upLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Registration activity
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivityForResult(intent, REQUEST_SIGN_UP);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private boolean login() {
        Log.d(TAG, "Login");

        final Model model = Model.getInstance();

        _loginButton.setEnabled(false);

        System.out.println(model.getUserHashMap().toString());

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        progressDialog.dismiss();


        EditText _usernameText = (EditText) findViewById(R.id.username);
        EditText _passwordText = (EditText) findViewById(R.id.password);

        final String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        mAuth.signInWithEmailAndPassword(username + "@waterworks.com", password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());


                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Login failed",
                                    Toast.LENGTH_SHORT).show();

                            result = false;

                        } else {
                            result = true;
                        }
                    }
                });

        new android.os.Handler().postDelayed(
                new Runnable() {

                    @Override
                    public void run() {
                        model.setCurrentUser(model.getUserHashMap().get(username));
                    }
                }, 3000);

        return result;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGN_UP) {
            if (resultCode == RESULT_OK) {

                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

